package booking_movie.service.verification;

import booking_movie.entity.User;
import booking_movie.entity.Verification;
import booking_movie.repository.VerificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VerificationServiceImpl implements VerificationService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CAPTCHA_LENGTH = 6;
    private final VerificationRepository verificationRepository;

    @Override
    public Verification create(User user) {
        Verification verification = new Verification();
        verification.setUser(user);
        verification.setVerificationCode(UUID.randomUUID().toString().substring(0, 6));
        verification.setCreatAt(LocalDate.now());
        verificationRepository.save(verification);
        return verification;
    }

    @Override
    public List<Verification> getVerifications(User user) {
        return verificationRepository.findAll();
    }

    @Override
    public boolean isExpired(Date date) {
        Instant currentInstant = Instant.now();
        Instant targetInstant = date.toInstant();

        Duration duration = Duration.between(targetInstant, currentInstant);
        long minutesDifference = duration.toMinutes();

        return minutesDifference >= 5;
    }

    public String generateRandomCaptcha() {
        SecureRandom random = new SecureRandom();
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            captcha.append(CHARACTERS.charAt(randomIndex));
        }
        return captcha.toString();
    }

}
