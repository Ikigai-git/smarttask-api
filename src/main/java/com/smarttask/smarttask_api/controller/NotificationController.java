package com.smarttask.smarttask_api.controller;

import com.smarttask.smarttask_api.model.Notifications;
import com.smarttask.smarttask_api.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Get all notifications for user
    @GetMapping("/{username}")
    public ResponseEntity<List<Notifications>> getNotifications(
            @PathVariable String username) {
        return ResponseEntity.ok(
                notificationService.getNotifications(username));
    }

    // Get unread notifications
    @GetMapping("/{username}/unread")
    public ResponseEntity<List<Notifications>> getUnread(
            @PathVariable String username) {
        return ResponseEntity.ok(
                notificationService.getUnreadNotifications(username));
    }

    // Get unread count
    @GetMapping("/{username}/count")
    public ResponseEntity<Map<String, Object>> getUnreadCount(
            @PathVariable String username) {
        return ResponseEntity.ok(
                notificationService.getUnreadCount(username));
    }

    // Mark single notification as read
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<Map<String, Object>> markAsRead(
            @PathVariable Integer notificationId) {
        return ResponseEntity.ok(
                notificationService.markAsRead(notificationId));
    }

    // Mark all as read
    @PutMapping("/{username}/read-all")
    public ResponseEntity<Map<String, Object>> markAllAsRead(
            @PathVariable String username) {
        return ResponseEntity.ok(
                notificationService.markAllAsRead(username));
    }
}