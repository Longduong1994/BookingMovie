package booking_movie.service.verification;


import booking_movie.entity.User;
import booking_movie.entity.Verification;

import java.util.Date;
import java.util.List;

public interface VerificationService {

    Verification create(User user);

    List<Verification> getVerifications(User user);

    boolean isExpired(Date date);
    String generateRandomCaptcha();
}
