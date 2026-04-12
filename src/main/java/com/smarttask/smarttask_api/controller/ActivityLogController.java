package com.smarttask.smarttask_api.controller;

import com.smarttask.smarttask_api.model.ActivityLogs;
import com.smarttask.smarttask_api.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping("/all")
    public ResponseEntity<List<ActivityLogs>> getAllLogs() {
        return ResponseEntity.ok(activityLogService.getAllLogs());
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<ActivityLogs>> getByUsername(
            @PathVariable String username) {
        return ResponseEntity.ok(
                activityLogService.getLogsByUsername(username));
    }
}