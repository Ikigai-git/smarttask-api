package com.smarttask.smarttask_api.service;

import com.smarttask.smarttask_api.model.Login;
import com.smarttask.smarttask_api.model.UserDetails;
import com.smarttask.smarttask_api.repository.LoginRepository;
import com.smarttask.smarttask_api.repository.UserDetailsRepository;
import com.smarttask.smarttask_api.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private static final long OTP_VALIDITY_MILLIS = 10 * 60 * 1000;

    private record PasswordResetOtp(String otp, long expiresAt) {
    }

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ActivityLogService activityLogService;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String mailFrom;

    @Value("${spring.mail.password:}")
    private String mailPassword;

    private final Map<String, PasswordResetOtp> passwordResetOtps = new ConcurrentHashMap<>();

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> response = new HashMap<>();

        Login login = loginRepository.findById(username).orElse(null);

        if (login == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        if (login.getStatus() == 0) {
            response.put("success", false);
            response.put("message", "Account is inactive");
            return response;
        }

        if (!passwordEncoder.matches(password, login.getPassword())) {
            response.put("success", false);
            response.put("message", "Invalid password");
            return response;
        }

        String token = jwtUtil.generateToken(username, login.getRole());
        UserDetails userDetails = userDetailsRepository.findByUsername(username);

        response.put("success", true);
        response.put("token", token);
        response.put("role", login.getRole());
        response.put("username", username);
        response.put("name", userDetails != null ? userDetails.getName() : username);
        response.put("message", "Login successful");

        activityLogService.log(username, "LOGIN", "User logged in successfully");

        return response;
    }

    public Map<String, Object> requestPasswordResetOtp(String username) {
        Map<String, Object> response = new HashMap<>();

        Login login = loginRepository.findById(username).orElse(null);
        if (login == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        UserDetails userDetails = userDetailsRepository.findByUsername(username);
        if (userDetails == null) {
            response.put("success", false);
            response.put("message", "User details not found");
            return response;
        }

        if (login.getStatus() == 0) {
            response.put("success", false);
            response.put("message", "Account is inactive");
            return response;
        }

        if (userDetails.getEmail() == null || userDetails.getEmail().isBlank()) {
            response.put("success", false);
            response.put("message", "No email is registered for this user. Please contact the super admin.");
            return response;
        }

        if (mailFrom == null || mailFrom.isBlank()) {
            response.put("success", false);
            response.put("message", "SMTP username is not configured.");
            return response;
        }

        if (mailPassword == null || mailPassword.isBlank()) {
            response.put("success", false);
            response.put("message", "SMTP password is not configured. Set SMARTTASK_MAIL_PASSWORD and restart the backend.");
            return response;
        }

        String otp = String.format("%06d", ThreadLocalRandom.current().nextInt(100000, 1000000));
        passwordResetOtps.put(username, new PasswordResetOtp(otp, System.currentTimeMillis() + OTP_VALIDITY_MILLIS));

        SimpleMailMessage message = new SimpleMailMessage();
        if (!mailFrom.isBlank()) {
            message.setFrom(mailFrom);
        }
        message.setTo(userDetails.getEmail());
        message.setSubject("SmartTask Password Reset OTP");
        message.setText(
                "Hello " + (userDetails.getName() != null ? userDetails.getName() : username) + ",\n\n"
                        + "Your SmartTask password reset OTP is: " + otp + "\n"
                        + "This OTP is valid for 10 minutes.\n\n"
                        + "If you did not request this, please ignore this email.");
        try {
            mailSender.send(message);
        } catch (MailException ex) {
            passwordResetOtps.remove(username);
            logger.error("Failed to send password reset OTP email for user {}", username, ex);
            String rootCause = ex.getMostSpecificCause() != null
                    ? ex.getMostSpecificCause().getMessage()
                    : ex.getMessage();
            response.put("success", false);
            response.put("message", "Unable to send OTP email: " + rootCause);
            return response;
        }

        activityLogService.log(username, "PASSWORD_RESET_OTP_SENT", "Password reset OTP sent to registered email");

        response.put("success", true);
        response.put("message", "OTP sent to the email registered for this username.");
        return response;
    }

    public Map<String, Object> verifyPasswordResetOtp(String username, String otp, String newPassword) {
        Map<String, Object> response = new HashMap<>();

        Login login = loginRepository.findById(username).orElse(null);
        if (login == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        PasswordResetOtp storedOtp = passwordResetOtps.get(username);
        if (storedOtp == null) {
            response.put("success", false);
            response.put("message", "No OTP request found. Please request a new OTP.");
            return response;
        }

        if (storedOtp.expiresAt() < System.currentTimeMillis()) {
            passwordResetOtps.remove(username);
            response.put("success", false);
            response.put("message", "OTP has expired. Please request a new OTP.");
            return response;
        }

        if (!Objects.equals(storedOtp.otp(), otp)) {
            response.put("success", false);
            response.put("message", "Invalid OTP");
            return response;
        }

        login.setPassword(passwordEncoder.encode(newPassword));
        loginRepository.save(login);
        passwordResetOtps.remove(username);

        activityLogService.log(username, "PASSWORD_RESET", "Password reset completed using OTP");

        response.put("success", true);
        response.put("message", "Password reset successful. Please sign in with your new password.");
        return response;
    }
}
