package com.smarttask.smarttask_api.service;

import com.smarttask.smarttask_api.model.Login;
import com.smarttask.smarttask_api.model.UserDetails;
import com.smarttask.smarttask_api.repository.LoginRepository;
import com.smarttask.smarttask_api.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get all users
    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }

    // Get users by role
    public List<UserDetails> getUsersByRole(String role) {
        List<Login> logins = loginRepository.findByRole(role);
        List<String> usernames = logins.stream()
                .map(Login::getUsername)
                .collect(java.util.stream.Collectors.toList());
        return userDetailsRepository.findByUsernameIn(usernames);
    }

    // Add new user
    public Map<String, Object> addUser(String username, String password,
            String role, UserDetails details) {
        Map<String, Object> response = new HashMap<>();

        if (loginRepository.existsById(username)) {
            response.put("success", false);
            response.put("message", "Username already exists");
            return response;
        }

        Login login = new Login();
        login.setUsername(username);
        login.setPassword(passwordEncoder.encode(password));
        login.setRole(role);
        login.setStatus(1);
        loginRepository.save(login);

        details.setUsername(username);
        details.setStatus(1);
        userDetailsRepository.save(details);

        response.put("success", true);
        response.put("message", "User created successfully");
        return response;
    }

    // Update user status (activate/deactivate)
    public Map<String, Object> updateStatus(String username, Integer status) {
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

        login.setStatus(status);
        userDetails.setStatus(status);
        loginRepository.save(login);
        userDetailsRepository.save(userDetails);
        response.put("success", true);
        response.put("message", "Status updated");
        return response;
    }

    // Get user by username
    public UserDetails getUserByUsername(String username) {
        return userDetailsRepository.findByUsername(username);
    }

    public Map<String, Object> updateUserDetails(String username, UserDetails updatedDetails) {
        Map<String, Object> response = new HashMap<>();

        Login login = loginRepository.findById(username).orElse(null);
        if (login == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        UserDetails existingDetails = userDetailsRepository.findByUsername(username);
        if (existingDetails == null) {
            response.put("success", false);
            response.put("message", "User details not found");
            return response;
        }

        existingDetails.setName(updatedDetails.getName());
        existingDetails.setEmail(updatedDetails.getEmail());
        existingDetails.setGender(updatedDetails.getGender());
        existingDetails.setContact(updatedDetails.getContact());
        existingDetails.setDepartment(updatedDetails.getDepartment());
        existingDetails.setDesignation(updatedDetails.getDesignation());
        existingDetails.setAddress(updatedDetails.getAddress());

        userDetailsRepository.save(existingDetails);

        boolean hasPasswordUpdate = updatedDetails.getPassword() != null
                && !updatedDetails.getPassword().isBlank();
        if (hasPasswordUpdate) {
            login.setPassword(passwordEncoder.encode(updatedDetails.getPassword()));
            loginRepository.save(login);
        }

        response.put("success", true);
        response.put("message", hasPasswordUpdate
                ? "User details and password updated successfully"
                : "User details updated successfully");
        response.put("user", existingDetails);
        return response;
    }
}
