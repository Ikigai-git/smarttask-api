package com.smarttask.smarttask_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "notifications")
public class Notifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;
    private String sentTo;
    private String sentBy;
    private String message;
    private String dateSent;
    private String type;
    private Integer status;
}