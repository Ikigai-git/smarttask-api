package com.smarttask.smarttask_api.controller;

import com.smarttask.smarttask_api.model.Tasks;
import com.smarttask.smarttask_api.model.TaskUpdates;
import com.smarttask.smarttask_api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Create task
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createTask(
            @RequestBody Tasks task) {
        return ResponseEntity.ok(taskService.createTask(task));
    }

    // Get all tasks (admin)
    @GetMapping("/all")
    public ResponseEntity<List<Tasks>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    // Get tasks by manager
    @GetMapping("/manager/{username}")
    public ResponseEntity<List<Tasks>> getTasksByManager(
            @PathVariable String username) {
        return ResponseEntity.ok(taskService.getTasksByManager(username));
    }

    // Get tasks by employee
    @GetMapping("/employee/{username}")
    public ResponseEntity<List<Tasks>> getTasksByEmployee(
            @PathVariable String username) {
        return ResponseEntity.ok(taskService.getTasksByEmployee(username));
    }

    // Get task by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<Tasks> getTaskById(
            @PathVariable Integer taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    // Update task status
    @PutMapping("/{taskId}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(
            @PathVariable Integer taskId,
            @RequestBody Map<String, String> request) {
        return ResponseEntity.ok(
                taskService.updateTaskStatus(taskId, request.get("status")));
    }

    // Add task update by employee
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> addTaskUpdate(
            @RequestBody TaskUpdates update) {
        return ResponseEntity.ok(taskService.addTaskUpdate(update));
    }

    // Get task updates
    @GetMapping("/{taskId}/updates")
    public ResponseEntity<List<TaskUpdates>> getTaskUpdates(
            @PathVariable Integer taskId) {
        return ResponseEntity.ok(taskService.getTaskUpdates(taskId));
    }

    // Delete task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Map<String, Object>> deleteTask(
            @PathVariable Integer taskId) {
        return ResponseEntity.ok(taskService.deleteTask(taskId));
    }
}