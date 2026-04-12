package com.smarttask.smarttask_api.controller;

import com.smarttask.smarttask_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        Map<String, Object> response = authService.login(username, password);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password/request-otp")
    public ResponseEntity<Map<String, Object>> requestForgotPasswordOtp(
            @RequestBody Map<String, String> request) {

        String username = request.get("username");
        Map<String, Object> response = authService.requestPasswordResetOtp(username);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password/verify-otp")
    public ResponseEntity<Map<String, Object>> verifyForgotPasswordOtp(
            @RequestBody Map<String, String> request) {

        String username = request.get("username");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        Map<String, Object> response = authService.verifyPasswordResetOtp(username, otp, newPassword);
        return ResponseEntity.ok(response);
    }
}
