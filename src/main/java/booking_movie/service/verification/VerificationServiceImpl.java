package booking_movie.service.verification;

import booking_movie.entity.User;
import booking_movie.entity.Verification;
import booking_movie.exception.CustomsException;
import booking_movie.repository.VerificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private final VerificationRepository verificationRepository;

    @Override
    public Verification create(User user) {
        Verification verification = new Verification();
        verification.setUser(user);
        verification.setVerificationCode(UUID.randomUUID().toString().substring(0, 6));
        verification.setCreatAt(LocalDateTime.now());
        verification.setStatus(true);
        verificationRepository.save(verification);
        return verification;
    }

    @Override
    public List<Verification> getVerifications(User user) {
        return verificationRepository.findAll();
    }

    @Override
    public boolean isExpired(String verificationCode) throws CustomsException {
        Verification verification = verificationRepository.findByVerificationCode(verificationCode);
        if (verification == null) {
            throw new CustomsException("Mã không đúng");
        }
        LocalDateTime createdAt = verification.getCreatAt();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Duration duration = Duration.between(createdAt, currentDateTime);
        long minutesDifference = duration.toMinutes();
        return minutesDifference < 5;
    }

    @Override
    public void resetVerification(String code) throws CustomsException {
        Verification verification = verificationRepository.findByVerificationCode(code);
        if (verification == null) {
            throw new CustomsException("Mã không đúng");
        }
        verification.setStatus(false);
        verificationRepository.save(verification);
    }

    @Override
    public boolean isVerification(String verification, User user) {
        List<Verification> verifications = verificationRepository.findByUser(user);
        if (!verifications.isEmpty()) {
            Verification latestVerification = verifications.get(verifications.size() - 1);
            if (latestVerification.isStatus() == false) {
                return false;
            }
            return latestVerification.getVerificationCode().equals(verification);
        }
        return false;
    }



}
