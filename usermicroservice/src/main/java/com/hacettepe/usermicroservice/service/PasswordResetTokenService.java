package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.exception.EmailSendingException;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import com.hacettepe.usermicroservice.model.PasswordResetToken;
import com.hacettepe.usermicroservice.model.User;
import com.hacettepe.usermicroservice.repository.IPasswordResetTokenRepository;
import com.hacettepe.usermicroservice.repository.IUserRepository;
import com.hacettepe.usermicroservice.utils.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetTokenService implements IPasswordResetTokenService {

    private final IUserRepository userRepository;
    private final IPasswordResetTokenRepository passwordResetTokenRepository;
    private final IUserService userService;
    private final IEmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public String createToken(String email) throws UserNotFoundException, EmailSendingException {
        log.info("here1");
        email = email.replace("\"", "");
        log.info(email);
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User with email " + email + " not found");
        }
        String token = UUID.randomUUID().toString();
        Date expiryDate = calculateExpiryDate();

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        passwordResetToken.setExpiryDate(expiryDate);
        passwordResetTokenRepository.save(passwordResetToken);

        sendPasswordResetEmail(user, token);

        return token;
    }

    private Date calculateExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 1);
        return calendar.getTime();
    }

    private void sendPasswordResetEmail(User user, String token) throws EmailSendingException {
        log.info("here2");
        String userEmail = user.getEmail();
        String subject = "Password Reset Request";
        String resetLink = "http://localhost:3000/resetPassword/" + token;
        String emailText = "Dear " + user.getName() + ",\n\n"
                + "Please click below link to reset your password.\n"
                + resetLink + "\n\n"
                + "If you did not request a reset, you can ignore this email.\n";

        emailService.sendPasswordResetEmail(userEmail, subject, emailText);
    }

    public void resetPassword(String token, String newPassword) {
        log.info("here3");
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElse(null);
        if (passwordResetToken == null)
            return;

        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    public boolean validatePasswordResetToken(String token) {
        log.info("here4");
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token).orElse(null);
        if (passwordResetToken == null)
            return false;
        return !isTokenExpired(passwordResetToken.getExpiryDate());
    }

    private boolean isTokenExpired(Date expiryDate) {
        return expiryDate.before(new Date());
    }

}
