package booking_movie.controller;

import booking_movie.entity.Order;
import booking_movie.entity.User;
import booking_movie.repository.OrderRepository;
import booking_movie.repository.PaymentRepository;
import booking_movie.repository.UserRepository;
import booking_movie.service.order.OrderService;
import booking_movie.service.payment.MomoService;
import booking_movie.service.payment.PayPalService;
import booking_movie.service.payment.VNPayService;
import booking_movie.service.payment.ZaLoPayService;
import booking_movie.service.user.UserService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.http.client.ClientProtocolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RestController
@RequestMapping("/api/booking/v1/payments")
@AllArgsConstructor
public class PaymentController {
    private final VNPayService vnPayService;
    private final PayPalService paypalService;
    private final MomoService momoService;
    private final ZaLoPayService zaLoPayService;
    private final OrderService orderService;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final UserService userService;



    //Momo
    @PostMapping("/payMomo")
    public ResponseEntity<?> createPayment(HttpServletRequest request,
                                                @RequestParam Long amount,
                                                @RequestParam Long orderId) throws NoSuchAlgorithmException, IOException, InvalidKeyException {
        return new ResponseEntity<>(momoService.createPayment(request,amount,orderId),HttpStatus.OK);
    }

    @PostMapping(value = "/transactionStatus")
    public Map<String, Object> transactionStatus(HttpServletRequest request, @RequestParam String requestId,
                                                 @RequestParam String orderId)
            throws InvalidKeyException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        return momoService.transactionStatus(request, requestId, orderId);
    }



    // Paypal
        @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestParam Double total,
                                                @RequestParam(defaultValue = "USD") String currency,
                                                @RequestParam(defaultValue = "CREDIT_CARD") String method,
                                                @RequestParam(defaultValue = "SALE") String intent,
                                                @RequestParam(defaultValue = "Thanh toán") String description) {
        try {
            Payment payment = paypalService.createPayment(total, currency, method,
                    intent, description, "http://localhost:6789/api/booking/payments/cancel",
                    "http://localhost:6789/api/booking/payments/success");

            for(Links link : payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok(link.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment");
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancelPayment() {
        return ResponseEntity.ok("Payment canceled");
    }

    @GetMapping("/success")
    public ResponseEntity<String> successPayment(@RequestParam("paymentId") String paymentId,
                                                 @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return ResponseEntity.ok("Payment successful");
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment unsuccessful");
    }


    //VNPay
    @GetMapping("/createVNPay")
    public ResponseEntity<?> submitOrder(@RequestParam String total,
                                         @RequestParam String orderCode) throws UnsupportedEncodingException {
        Long amount = Long.parseLong(total);
        return new ResponseEntity<>(vnPayService.createOrder(amount,orderCode),HttpStatus.OK);
    }

    @GetMapping("payment-callback")
    public void paymentCallback(@RequestParam Map<String, String> queryParams,HttpServletResponse response) throws IOException {
        String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");
        String orderCode = queryParams.get("vnp_OrderInfo");
        Order order = orderService.findByCode(orderCode);
        User user = order.getUser();
        Optional<booking_movie.entity.Payment> payment = paymentRepository.findById(1L);

            if ("00".equals(vnp_ResponseCode)) {
                // Giao dịch thành công
                // Thực hiện các xử lý cần thiết, ví dụ: cập nhật CSDL
                order.setPayment(payment.get());
                orderRepository.save(order);
                user.setPoint(user.getPoint() + order.getTotal()/1000 - order.getPoint());
                userRepository.save(user);
                userService.checkSumTotal(user);
                String redirectUrl = "http://localhost:3000/paymentSuccess?orderId=" + order.getId();
                response.sendRedirect(redirectUrl);
            } else {
                orderService.deleteOrder(order.getId());
                // Giao dịch thất bại
                // Thực hiện các xử lý cần thiết, ví dụ: không cập nhật CSDL\
                response.sendRedirect("http://localhost:3000/error");

            }
    }


    // ZaLoPay

    @PostMapping(value = "/create-order")
    public Map<String, Object> createPayment(HttpServletRequest request,
                                             @RequestParam(name = "appuser") String appuser,
                                             @RequestParam(name = "amount") Long amount,
                                             @RequestParam(name ="order_id") Long order_id) throws Exception {
        return zaLoPayService.createPayment(request, appuser, amount, order_id);
    }

    @GetMapping(value = "/getstatusbyapptransid")
    public Map<String, Object> getStatusByApptransid(HttpServletRequest request,
                                                     @RequestParam(name = "apptransid") String apptransid) throws Exception {
        return zaLoPayService.getStatusByApptransid(request, apptransid);
    }
}
