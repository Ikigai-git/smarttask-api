package com.smarttask.smarttask_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "activitylogs")
public class ActivityLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;
    private String username;
    private String action;
    private String timestamp;
    private String details;
}
