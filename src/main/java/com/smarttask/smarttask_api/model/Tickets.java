package com.smarttask.smarttask_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tickets")
public class Tickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;
    private String employeeUsername;
    private String managerUsername;
    private String subject;
    private String message;
    private String createdDate;
    private String reply;
    private String replyDate;
    private String status;
}
