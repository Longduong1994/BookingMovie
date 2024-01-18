package booking_movie.controller;

import booking_movie.service.payment.MomoService;
import booking_movie.service.payment.PayPalService;
import booking_movie.service.payment.VNPayService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/api/booking/v1/payments")
@AllArgsConstructor
public class PaymentController {
    private final VNPayService vnPayService;
    private final PayPalService paypalService;
    private final MomoService momoService;


    @PostMapping("/payMomo")
    public ResponseEntity<String> createPayment(@RequestParam String orderId,
                                                @RequestParam String orderInfo,
                                                @RequestParam String orderPrice) {
        return ResponseEntity.status(HttpStatus.OK).body(momoService.makePayment(orderId, orderInfo, orderPrice));
    }



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

    @GetMapping("/pay")
    public String getPay(@RequestParam(defaultValue = "10000") long price,@RequestParam(defaultValue = "1") Long orderId) throws UnsupportedEncodingException {

        return vnPayService.createPayment(price, orderId);
    }

    @GetMapping("payment-callback")
    public void paymentCallback(@RequestParam Map<String, String> queryParams, HttpServletResponse response) throws IOException {

    }
}
