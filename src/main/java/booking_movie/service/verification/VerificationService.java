package booking_movie.service.verification;


import booking_movie.entity.User;
import booking_movie.entity.Verification;
import booking_movie.exception.CustomsException;

import java.util.Date;
import java.util.List;

public interface VerificationService {

    Verification create(User user);
    void resetVerification(String code) throws CustomsException;

    List<Verification> getVerifications(User user);

    boolean isExpired(String verification) throws CustomsException;
    boolean isVerification(String verification,User user);
}
