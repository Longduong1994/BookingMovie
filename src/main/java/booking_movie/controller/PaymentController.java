package booking_movie.controller;

import booking_movie.config.VNPayConfig;
import booking_movie.service.payment.VNPayService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/booking/v1/payments")
@AllArgsConstructor
public class PaymentController {
    private VNPayService vnPayService;


    @GetMapping("/create")
    public String getPay(@RequestParam(defaultValue = "10000") long price,@RequestParam(defaultValue = "1") Integer contractId) throws UnsupportedEncodingException{



        return paymentUrl;
    }

}
