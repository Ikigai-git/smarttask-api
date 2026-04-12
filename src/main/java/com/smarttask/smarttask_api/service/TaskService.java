package com.smarttask.smarttask_api.service;

import com.smarttask.smarttask_api.model.Tasks;
import com.smarttask.smarttask_api.model.TaskUpdates;
import com.smarttask.smarttask_api.model.Notifications;
import com.smarttask.smarttask_api.repository.TasksRepository;
import com.smarttask.smarttask_api.repository.TaskUpdatesRepository;
import com.smarttask.smarttask_api.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private TaskUpdatesRepository taskUpdatesRepository;

    @Autowired
    private NotificationsRepository notificationsRepository;

    private String getCurrentDateTime() {
        return LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Create task
    public Map<String, Object> createTask(Tasks task) {
        Map<String, Object> response = new HashMap<>();
        task.setStatus("Pending");
        Tasks saved = tasksRepository.save(task);

        // Create notification for employee
        Notifications notif = new Notifications();
        notif.setSentTo(task.getEmployeeUsername());
        notif.setSentBy(task.getManagerUsername());
        notif.setMessage("New task assigned: " + task.getTitle());
        notif.setDateSent(getCurrentDateTime());
        notif.setType("TASK");
        notif.setStatus(0);
        notificationsRepository.save(notif);

        response.put("success", true);
        response.put("message", "Task created successfully");
        response.put("task", saved);
        return response;
    }

    // Get tasks by manager
    public List<Tasks> getTasksByManager(String managerUsername) {
        return tasksRepository.findByManagerUsername(managerUsername);
    }

    // Get tasks by employee
    public List<Tasks> getTasksByEmployee(String employeeUsername) {
        return tasksRepository.findByEmployeeUsername(employeeUsername);
    }

    // Get all tasks
    public List<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }

    // Get task by ID
    public Tasks getTaskById(Integer taskId) {
        return tasksRepository.findById(taskId).orElse(null);
    }

    // Update task status
    public Map<String, Object> updateTaskStatus(Integer taskId, String status) {
        Map<String, Object> response = new HashMap<>();
        Tasks task = tasksRepository.findById(taskId).orElse(null);
        if (task == null) {
            response.put("success", false);
            response.put("message", "Task not found");
            return response;
        }
        task.setStatus(status);
        tasksRepository.save(task);
        response.put("success", true);
        response.put("message", "Task status updated");
        return response;
    }

    // Employee updates task progress
    public Map<String, Object> addTaskUpdate(TaskUpdates update) {
        Map<String, Object> response = new HashMap<>();
        update.setUpdateDate(getCurrentDateTime());
        taskUpdatesRepository.save(update);

        // Notify manager
        Tasks task = tasksRepository.findById(update.getTaskId()).orElse(null);
        if (task != null) {
            Notifications notif = new Notifications();
            notif.setSentTo(task.getManagerUsername());
            notif.setSentBy(update.getEmployeeUsername());
            notif.setMessage("Task updated: " + task.getTitle()
                    + " - " + update.getUpdateMessage());
            notif.setDateSent(getCurrentDateTime());
            notif.setType("UPDATE");
            notif.setStatus(0);
            notificationsRepository.save(notif);
        }

        response.put("success", true);
        response.put("message", "Task update added");
        return response;
    }

    // Get task updates
    public List<TaskUpdates> getTaskUpdates(Integer taskId) {
        return taskUpdatesRepository.findByTaskId(taskId);
    }

    // Delete task
    public Map<String, Object> deleteTask(Integer taskId) {
        Map<String, Object> response = new HashMap<>();
        tasksRepository.deleteById(taskId);
        response.put("success", true);
        response.put("message", "Task deleted");
        return response;
    }
}