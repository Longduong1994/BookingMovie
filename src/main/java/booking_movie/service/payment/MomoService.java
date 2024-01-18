package booking_movie.service.payment;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MomoService {

    private final String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";
    private final String partnerCode = "MOMOBKUN20180529";
    private final String accessKey = "klm05TvNBzhg7h7j";
    private final String secretKey = "at67qH6mk8w5Y1nAyMoYKMWACiEi2bsa";
    private final String redirectUrl = "http://localhost:82/Java/payment/momo/success";
    private final String ipnUrl = "http://localhost:82/Java/payment/momo/success";

    public String makePayment(String orderId, String orderInfo, String orderPrice) {
        String extraData = "";
        String requestId = String.valueOf(System.currentTimeMillis());
        String requestType = "payWithATM";

        String rawHash = "accessKey=" + accessKey + "&amount=" + orderPrice + "&extraData=" + extraData +
                "&ipnUrl=" + ipnUrl + "&orderId=" + orderId + "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode + "&redirectUrl=" + redirectUrl + "&requestId=" + requestId +
                "&requestType=" + requestType;

        String signature = org.apache.commons.codec.digest.DigestUtils.sha256Hex(rawHash);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> data = new HashMap<>();
        data.put("partnerCode", partnerCode);
        data.put("partnerName", "Test");
        data.put("storeId", "MomoTestStore");
        data.put("requestId", requestId);
        data.put("amount", orderPrice);
        data.put("orderId", orderId);
        data.put("orderInfo", orderInfo);
        data.put("redirectUrl", redirectUrl);
        data.put("ipnUrl", ipnUrl);
        data.put("lang", "vi");
        data.put("extraData", extraData);
        data.put("requestType", requestType);
        data.put("signature", signature);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(data, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, requestEntity, String.class);

        return response.getBody();
    }

    public String handlePaymentResult(String vnpResponseCode, String resultCode) {
        if ("00".equals(vnpResponseCode) || "00".equals(resultCode)) {
            return "http://localhost:6789/api/booking/payments/success";
        } else {
            return "http://localhost:6789/api/booking/payments/error";
        }
    }
}
