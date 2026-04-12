package com.smarttask.smarttask_api.service;

import com.smarttask.smarttask_api.model.Tickets;
import com.smarttask.smarttask_api.model.Notifications;
import com.smarttask.smarttask_api.repository.TicketsRepository;
import com.smarttask.smarttask_api.repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketService {

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private NotificationsRepository notificationsRepository;

    private String getCurrentDateTime() {
        return LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Raise ticket
    public Map<String, Object> raiseTicket(Tickets ticket) {
        Map<String, Object> response = new HashMap<>();
        ticket.setCreatedDate(getCurrentDateTime());
        ticket.setStatus("Open");
        ticketsRepository.save(ticket);

        // Notify manager
        Notifications notif = new Notifications();
        notif.setSentTo(ticket.getManagerUsername());
        notif.setSentBy(ticket.getEmployeeUsername());
        notif.setMessage("New ticket raised: " + ticket.getSubject());
        notif.setDateSent(getCurrentDateTime());
        notif.setType("TICKET");
        notif.setStatus(0);
        notificationsRepository.save(notif);

        response.put("success", true);
        response.put("message", "Ticket raised successfully");
        return response;
    }

    // Get tickets by employee
    public List<Tickets> getTicketsByEmployee(String employeeUsername) {
        return ticketsRepository.findByEmployeeUsername(employeeUsername);
    }

    // Get tickets by manager
    public List<Tickets> getTicketsByManager(String managerUsername) {
        return ticketsRepository.findByManagerUsername(managerUsername);
    }

    // Get all tickets
    public List<Tickets> getAllTickets() {
        return ticketsRepository.findAll();
    }

    // Reply to ticket
    public Map<String, Object> replyTicket(Integer ticketId,
            String reply) {
        Map<String, Object> response = new HashMap<>();
        Tickets ticket = ticketsRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            response.put("success", false);
            response.put("message", "Ticket not found");
            return response;
        }
        ticket.setReply(reply);
        ticket.setReplyDate(getCurrentDateTime());
        ticket.setStatus("Resolved");
        ticketsRepository.save(ticket);

        // Notify employee
        Notifications notif = new Notifications();
        notif.setSentTo(ticket.getEmployeeUsername());
        notif.setSentBy(ticket.getManagerUsername());
        notif.setMessage("Your ticket has been resolved: "
                + ticket.getSubject());
        notif.setDateSent(getCurrentDateTime());
        notif.setType("TICKET");
        notif.setStatus(0);
        notificationsRepository.save(notif);

        response.put("success", true);
        response.put("message", "Ticket replied successfully");
        return response;
    }

    // Update ticket status
    public Map<String, Object> updateTicketStatus(Integer ticketId,
            String status) {
        Map<String, Object> response = new HashMap<>();
        Tickets ticket = ticketsRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            response.put("success", false);
            response.put("message", "Ticket not found");
            return response;
        }
        ticket.setStatus(status);
        ticketsRepository.save(ticket);
        response.put("success", true);
        response.put("message", "Ticket status updated");
        return response;
    }
}