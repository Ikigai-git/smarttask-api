package com.smarttask.smarttask_api.controller;

import com.smarttask.smarttask_api.model.Tickets;
import com.smarttask.smarttask_api.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Raise ticket
    @PostMapping("/raise")
    public ResponseEntity<Map<String, Object>> raiseTicket(
            @RequestBody Tickets ticket) {
        return ResponseEntity.ok(ticketService.raiseTicket(ticket));
    }

    // Get all tickets (admin)
    @GetMapping("/all")
    public ResponseEntity<List<Tickets>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    // Get tickets by employee
    @GetMapping("/employee/{username}")
    public ResponseEntity<List<Tickets>> getByEmployee(
            @PathVariable String username) {
        return ResponseEntity.ok(
                ticketService.getTicketsByEmployee(username));
    }

    // Get tickets by manager
    @GetMapping("/manager/{username}")
    public ResponseEntity<List<Tickets>> getByManager(
            @PathVariable String username) {
        return ResponseEntity.ok(
                ticketService.getTicketsByManager(username));
    }

    // Reply to ticket
    @PutMapping("/{ticketId}/reply")
    public ResponseEntity<Map<String, Object>> replyTicket(
            @PathVariable Integer ticketId,
            @RequestBody Map<String, String> request) {
        return ResponseEntity.ok(
                ticketService.replyTicket(ticketId, request.get("reply")));
    }

    // Update ticket status
    @PutMapping("/{ticketId}/status")
    public ResponseEntity<Map<String, Object>> updateStatus(
            @PathVariable Integer ticketId,
            @RequestBody Map<String, String> request) {
        return ResponseEntity.ok(
                ticketService.updateTicketStatus(ticketId, request.get("status")));
    }
}