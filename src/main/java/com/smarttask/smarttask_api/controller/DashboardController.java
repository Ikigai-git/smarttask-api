package com.smarttask.smarttask_api.controller;

import com.smarttask.smarttask_api.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // Super Admin dashboard
    @GetMapping("/admin")
    public ResponseEntity<Map<String, Object>> getAdminDashboard() {
        return ResponseEntity.ok(
                dashboardService.getAdminDashboard());
    }

    // Manager dashboard
    @GetMapping("/manager/{username}")
    public ResponseEntity<Map<String, Object>> getManagerDashboard(
            @PathVariable String username) {
        return ResponseEntity.ok(
                dashboardService.getManagerDashboard(username));
    }

    // Employee dashboard
    @GetMapping("/employee/{username}")
    public ResponseEntity<Map<String, Object>> getEmployeeDashboard(
            @PathVariable String username) {
        return ResponseEntity.ok(
                dashboardService.getEmployeeDashboard(username));
    }
}