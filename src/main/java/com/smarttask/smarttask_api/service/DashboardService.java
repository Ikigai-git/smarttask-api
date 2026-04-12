package com.smarttask.smarttask_api.service;

import com.smarttask.smarttask_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private TasksRepository tasksRepository;

    @Autowired
    private TicketsRepository ticketsRepository;

    // @Autowired
    // private NotificationsRepository notificationsRepository;

    public Map<String, Object> getAdminDashboard() {
        Map<String, Object> response = new HashMap<>();

        // User counts
        long totalManagers = loginRepository.findByRole("MANAGER").size();
        long totalEmployees = loginRepository.findByRole("EMPLOYEE").size();

        // Task counts
        long totalTasks = tasksRepository.count();
        long pendingTasks = tasksRepository
                .findByStatus("Pending").size();
        long inProgressTasks = tasksRepository
                .findByStatus("In Progress").size();
        long completedTasks = tasksRepository
                .findByStatus("Completed").size();

        // Ticket counts
        long totalTickets = ticketsRepository.count();
        long openTickets = ticketsRepository
                .findByStatus("Open").size();
        long resolvedTickets = ticketsRepository
                .findByStatus("Resolved").size();

        response.put("totalManagers", totalManagers);
        response.put("totalEmployees", totalEmployees);
        response.put("totalTasks", totalTasks);
        response.put("pendingTasks", pendingTasks);
        response.put("inProgressTasks", inProgressTasks);
        response.put("completedTasks", completedTasks);
        response.put("totalTickets", totalTickets);
        response.put("openTickets", openTickets);
        response.put("resolvedTickets", resolvedTickets);

        return response;
    }

    public Map<String, Object> getManagerDashboard(String username) {
        Map<String, Object> response = new HashMap<>();

        long myTasks = tasksRepository
                .findByManagerUsername(username).size();
        long myPending = tasksRepository
                .findByManagerUsernameAndStatus(username, "Pending").size();
        long myInProgress = tasksRepository
                .findByManagerUsernameAndStatus(username, "In Progress").size();
        long myCompleted = tasksRepository
                .findByManagerUsernameAndStatus(username, "Completed").size();
        long myOpenTickets = ticketsRepository
                .findByManagerUsernameAndStatus(username, "Open").size();

        response.put("totalTasks", myTasks);
        response.put("pendingTasks", myPending);
        response.put("inProgressTasks", myInProgress);
        response.put("completedTasks", myCompleted);
        response.put("openTickets", myOpenTickets);

        return response;
    }

    public Map<String, Object> getEmployeeDashboard(String username) {
        Map<String, Object> response = new HashMap<>();

        long myTasks = tasksRepository
                .findByEmployeeUsername(username).size();
        long myPending = tasksRepository
                .findByEmployeeUsernameAndStatus(username, "Pending").size();
        long myInProgress = tasksRepository
                .findByEmployeeUsernameAndStatus(username, "In Progress").size();
        long myCompleted = tasksRepository
                .findByEmployeeUsernameAndStatus(username, "Completed").size();
        long myOpenTickets = ticketsRepository
                .findByEmployeeUsernameAndStatus(username, "Open").size();

        response.put("totalTasks", myTasks);
        response.put("pendingTasks", myPending);
        response.put("inProgressTasks", myInProgress);
        response.put("completedTasks", myCompleted);
        response.put("openTickets", myOpenTickets);

        return response;
    }
}