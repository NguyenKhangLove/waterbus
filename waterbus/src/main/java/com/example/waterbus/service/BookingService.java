package com.example.waterbus.service;

import com.example.waterbus.entity.*;
import com.example.waterbus.dto.req.TicketDetailReq;
import com.example.waterbus.dto.req.TicketReq;
import com.example.waterbus.dto.res.BookingInfo;
import com.example.waterbus.dto.res.BookingRes;
import com.example.waterbus.repository.*;
import com.example.waterbus.utils.QRCodeGenerator;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketSeatRepository ticketSeatRepository;
    @Autowired
    private TicketDetailRepository ticketDetailRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private QRCodeGenerator qrCodeGenerator;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private StationService stationService;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private TripRepository tripRepository;

    private final Map<String, TicketReq> previewCache = new ConcurrentHashMap<>();

    private int calculatePrice(int birthYear) {
        int age = LocalDateTime.now().getYear() - birthYear;
        if (age < 5 || age > 80) return 0;
        return 15000;
    }

    private Long getCategoryId(int birthYear) {
        int age = LocalDate.now().getYear() - birthYear;
        if (age < 5 || age > 80) return 1L; // miễn phí
        return 3L; // trả tiền5
    }

    public BookingRes createPreviewBooking(TicketReq req) throws Exception {
        // Validate số lượng ghế và chi tiết
        if (req.getSeatIds() == null || req.getSeatIds().isEmpty()) {
            throw new IllegalArgumentException("Danh sách ghế không được để trống");
        }
        if (req.getSeatIds().size() > 1 &&
                (req.getDetails() == null || req.getDetails().size() != req.getSeatIds().size())) {
            throw new IllegalArgumentException("Số lượng chi tiết người đi phải bằng số ghế");
        }

        // Tính tổng tiền
        int totalPrice = (req.getSeatIds().size() == 1)
                ? calculatePrice(req.getBirthYear())
                : req.getDetails().stream().mapToInt(d -> calculatePrice(d.getBirthYear())).sum();

        // Tạo mã tạm
        String tempId = UUID.randomUUID().toString();

        // Nếu thanh toán QR, tạo mã QR và lưu tạm
        if ("QR".equals(req.getPaymentMethod())) {
            String content = "Đặt vé: " + req.getFullname() +
                    "\nTừ: " + req.getStartStationId() +
                    "\nĐến: " + req.getEndStationId() +
                    "\nGiá: " + totalPrice;

            String qrContent = "Nội dung tiếng Việt có dấu: Đặt vé xe";
            String qrCode = qrCodeGenerator.generateQRCodeImage(content, 200, 200);
            previewCache.put(tempId, req);

            return new BookingRes(null, (double) totalPrice, qrCode, tempId);
        }
        // Nếu thanh toán tiền mặt, lưu luôn vào database
        else {
            Long ticketId = bookTicketImmediately(req);
            return new BookingRes(ticketId, (double) totalPrice, null, null);
        }
    }

    @Transactional(readOnly = false)
    public Long bookTicketImmediately(TicketReq req) {
        // 1. Tạo khách hàng
        Customer customer = new Customer();
        customer.setFullName(req.getFullname());
        customer.setBirthYear(req.getBirthYear());
        customer.setPhone(req.getPhone());
        customer.setEmail(req.getEmail());
        customer.setNationality(req.getNationality());
        customer = customerRepository.save(customer);

        // 2. Tạo vé
        Ticket ticket = new Ticket();
        ticket.setCustomer(customer);
        ticket.setStartStationId(req.getStartStationId());
        ticket.setEndStationId(req.getEndStationId());
        ticket.setIdTrip(req.getTripId());
        ticket.setIdStaff(req.getStaffId());
        ticket.setBookingTime(LocalDateTime.now());
        ticket.setPaymentMethod(req.getPaymentMethod());
        ticket.setSeatQuantity(req.getSeatIds().size());

        // Tính tổng tiền
        int totalPrice = (req.getSeatIds().size() == 1)
                ? calculatePrice(req.getBirthYear())
                : req.getDetails().stream().mapToInt(d -> calculatePrice(d.getBirthYear())).sum();
        ticket.setPrice((double) totalPrice);

        // Lưu vé
        ticket = ticketRepository.save(ticket);

        // 3. Lưu seat_ticket
        for (Long seatId : req.getSeatIds()) {
            ticketSeatRepository.save(new SeatTicket(seatId, ticket.getIdTicket()));
        }

        // 4. Nếu có nhiều hơn 1 ghế, lưu ticket_detail
        if (req.getSeatIds().size() > 1) {
            for (TicketDetailReq dto : req.getDetails()) {
                TicketDetail detail = new TicketDetail();
                detail.setTicket(ticket);
                detail.setFullName(dto.getFullname());
                detail.setBirthYear(dto.getBirthYear());
                Category category = categoryRepository.findById(getCategoryId(dto.getBirthYear()))
                        .orElseThrow(() -> new RuntimeException("Không tìm thấy loại vé"));
                detail.setCategory(category);
                ticketDetailRepository.save(detail);
            }
        }
///*************************************************************************************************************************
        // Lấy giờ khởi hành bằng stored procedure
        LocalTime startTime = ticketRepository.getStartTime(
                req.getTripId(),
                req.getStartStationId(),
                req.getEndStationId()
        );
        // Lấy tên tàu - số hiệu tàu
        Trip trip = tripRepository.findById(req.getTripId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chuyến đi"));
        Ship ship = trip.getShip();
        String shipInfo = ship.getName() + " - " + ship.getRegistrationNumber();

        // Lấy danh sách số ghế
        List<Seat> seats = seatRepository.findAllById(req.getSeatIds());
        String seatNumbers = seats.stream()
                .map(Seat::getSeatNumber)
                .collect(Collectors.joining(", "));

        // Lấy ngày khởi hành từ Trip
        LocalDate departureDate = trip.getDepartureDate();
        String formattedDepartureDate = departureDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));


        String formattedStartTime = startTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        String startStationName = stationService.getNameById(req.getStartStationId());
        String endStationName = stationService.getNameById(req.getEndStationId());
        String formattedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Chuẩn bị thông tin ghế và người (nếu có nhiều ghế)
        StringBuilder seatsDetails = new StringBuilder();
        if (req.getSeatIds().size() == 1) {
            // Nếu chỉ có 1 ghế, in thông tin cho khách hàng chính
            seatsDetails.append("<tr><td><strong>Khách hàng:</strong></td><td>")
                    .append(req.getFullname()).append(" - ").append(req.getBirthYear())
                    .append("</td></tr>");
        } else {
            // Nếu có nhiều ghế, in thông tin cho từng người
            for (TicketDetailReq dto : req.getDetails()) {
                seatsDetails.append("<tr><td><strong>Khách hàng:</strong></td><td>")
                        .append(dto.getFullname()).append(" - ").append(dto.getBirthYear())
                        .append("</td></tr>");
            }
        }
        // Nội dung email
        String htmlContent = "<html><body>"
                + "<h2 style='color: #007bff;'>Đặt vé thành công!</h2>"
                + "<p>Xin chào <strong>" + req.getFullname() + "</strong>,</p>"
                + "<table style='width: 100%; border: 1px solid #ddd; border-collapse: collapse;'>"
                + "<tr><td><strong>Trạm xuất phát:</strong></td><td>" + startStationName + "</td></tr>"
                + "<tr><td><strong>Trạm đích:</strong></td><td>" + endStationName + "</td></tr>"
                + "<tr><td><strong>Thời gian đặt:</strong></td><td>" + formattedTime + "</td></tr>"
                + "<tr><td><strong>Giờ khởi hành:</strong></td><td>" + formattedStartTime + "</td></tr>"
                + "<tr><td><strong>Ngày khởi hành:</strong></td><td>" + formattedDepartureDate + "</td></tr>"
                + "<tr><td><strong>Số ghế:</strong></td><td>" + seatNumbers + "</td></tr>"
                + "<tr><td><strong>Tàu:</strong></td><td>" + shipInfo + "</td></tr>"
                + seatsDetails.toString()
                + "<tr><td><strong>TỔNG TIỀN:</strong></td><td>" + totalPrice + " VND</td></tr>"
                + "</table>"  // <-- tổng tiền nằm trong bảng
                + "<p style='font-size: 0.8em;'>Đây là email tự động. Vui lòng không trả lời.</p>"  // phía sau bảng
                + "</body></html>";
         // Gửi email
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(req.getEmail());
            helper.setSubject("Thông báo đặt vé thành công!");
            helper.setText(htmlContent, true);  // true để chỉ định nội dung là HTML
            javaMailSender.send(message);

        } catch (Exception e) {
            System.err.println("Lỗi khi gửi email: " + e.getMessage());
        }
//*******************************************************************************************************************************
        return ticket.getIdTicket();
    }

    @Transactional(readOnly = false)
    public ResponseEntity<?> confirmQrPayment(String tempId) {
        TicketReq req = previewCache.get(tempId);
        if (req == null) {
            return ResponseEntity.badRequest().body("Mã đặt vé không hợp lệ hoặc đã hết hạn");
        }

        try {
            Long ticketId = bookTicketImmediately(req);
            previewCache.remove(tempId);

            // Tính toán lại totalPrice thay vì dùng getTotalPrice()
            int totalPrice = (req.getSeatIds().size() == 1)
                    ? calculatePrice(req.getBirthYear())
                    : req.getDetails().stream().mapToInt(d -> calculatePrice(d.getBirthYear())).sum();

            return ResponseEntity.ok(new BookingInfo(ticketId, (double) totalPrice));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi xác nhận thanh toán: " + e.getMessage());
        }
    }

    //********************************************************************************************************************



}
