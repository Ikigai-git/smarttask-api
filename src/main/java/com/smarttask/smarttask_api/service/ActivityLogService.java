package com.smarttask.smarttask_api.service;

import com.smarttask.smarttask_api.model.ActivityLogs;
import com.smarttask.smarttask_api.repository.ActivityLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ActivityLogService {

    @Autowired
    private ActivityLogsRepository activityLogsRepository;

    public void log(String username, String action, String details) {
        ActivityLogs log = new ActivityLogs();
        log.setUsername(username);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        log.setDetails(details);
        activityLogsRepository.save(log);
    }

    public List<ActivityLogs> getAllLogs() {
        return activityLogsRepository.findAll();
    }

    public List<ActivityLogs> getLogsByUsername(String username) {
        return activityLogsRepository.findByUsername(username);
    }
}