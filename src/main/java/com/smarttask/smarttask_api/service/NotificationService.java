package com.smarttask.smarttask_api.service;

import com.smarttask.smarttask_api.model.Notifications;
import com.smarttask.smarttask_api.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private NotificationsRepository notificationsRepository;

    // Get all notifications for a user
    public List<Notifications> getNotifications(String username) {
        return notificationsRepository.findBySentTo(username);
    }

    // Get unread notifications
    public List<Notifications> getUnreadNotifications(String username) {
        return notificationsRepository
                .findBySentToAndStatus(username, 0);
    }

    // Mark notification as read
    public Map<String, Object> markAsRead(Integer notificationId) {
        Map<String, Object> response = new HashMap<>();
        Notifications notif = notificationsRepository
                .findById(notificationId).orElse(null);
        if (notif == null) {
            response.put("success", false);
            response.put("message", "Notification not found");
            return response;
        }
        notif.setStatus(1);
        notificationsRepository.save(notif);
        response.put("success", true);
        response.put("message", "Marked as read");
        return response;
    }

    // Mark all as read for a user
    public Map<String, Object> markAllAsRead(String username) {
        Map<String, Object> response = new HashMap<>();
        List<Notifications> unread = notificationsRepository
                .findBySentToAndStatus(username, 0);
        unread.forEach(n -> n.setStatus(1));
        notificationsRepository.saveAll(unread);
        response.put("success", true);
        response.put("message", "All notifications marked as read");
        return response;
    }

    // Get unread count
    public Map<String, Object> getUnreadCount(String username) {
        Map<String, Object> response = new HashMap<>();
        int count = notificationsRepository
                .findBySentToAndStatus(username, 0).size();
        response.put("count", count);
        return response;
    }
}