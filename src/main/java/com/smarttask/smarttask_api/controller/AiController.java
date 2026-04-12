package com.smarttask.smarttask_api.controller;

import com.smarttask.smarttask_api.model.AiRecommendations;
import com.smarttask.smarttask_api.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private AiService aiService;

    // Rewrite task description
    @PostMapping("/rewrite")
    public ResponseEntity<Map<String, Object>> rewrite(
            @RequestBody Map<String, Object> request) {
        String description = (String) request.get("description");
        Integer taskId = request.get("taskId") != null ? (Integer) request.get("taskId") : null;
        String username = (String) request.get("username");
        return ResponseEntity.ok(
                aiService.rewriteTask(description, taskId, username));
    }

    // Summarize task description
    @PostMapping("/summarize")
    public ResponseEntity<Map<String, Object>> summarize(
            @RequestBody Map<String, Object> request) {
        String description = (String) request.get("description");
        Integer taskId = request.get("taskId") != null ? (Integer) request.get("taskId") : null;
        String username = (String) request.get("username");
        return ResponseEntity.ok(
                aiService.summarizeTask(description, taskId, username));
    }

    // Suggest deadline
    @PostMapping("/suggest-deadline")
    public ResponseEntity<Map<String, Object>> suggestDeadline(
            @RequestBody Map<String, String> request) {
        return ResponseEntity.ok(aiService.suggestDeadline(
                request.get("title"),
                request.get("description"),
                request.get("priority")));
    }

    // Get AI recommendations for a task
    @GetMapping("/recommendations/{taskId}")
    public ResponseEntity<List<AiRecommendations>> getRecommendations(
            @PathVariable Integer taskId) {
        return ResponseEntity.ok(aiService.getRecommendations(taskId));
    }
}