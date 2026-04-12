package com.smarttask.smarttask_api.controller;

import com.smarttask.smarttask_api.model.UserDetails;
import com.smarttask.smarttask_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<List<UserDetails>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get users by role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDetails>> getUsersByRole(
            @PathVariable String role) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    // Get user by username
    @GetMapping("/{username}")
    public ResponseEntity<UserDetails> getUserByUsername(
            @PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PutMapping("/{username}")
    public ResponseEntity<Map<String, Object>> updateUser(
            @PathVariable String username,
            @RequestBody UserDetails details) {
        return ResponseEntity.ok(userService.updateUserDetails(username, details));
    }

    // Add new user
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addUser(
            @RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        String password = (String) request.get("password");
        String role = (String) request.get("role");

        UserDetails details = new UserDetails();
        details.setName((String) request.get("name"));
        details.setEmail((String) request.get("email"));
        details.setGender((String) request.get("gender"));
        details.setContact((String) request.get("contact"));
        details.setDepartment((String) request.get("department"));
        details.setDesignation((String) request.get("designation"));
        details.setAddress((String) request.get("address"));

        return ResponseEntity.ok(
                userService.addUser(username, password, role, details));
    }

    // Update user status
    @PutMapping("/{username}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(
            @PathVariable String username,
            @RequestBody Map<String, Integer> request) {
        return ResponseEntity.ok(
                userService.updateStatus(username, request.get("status")));
    }
}
