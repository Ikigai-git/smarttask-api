package com.smarttask.smarttask_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;
    private String managerUsername;
    private String employeeUsername;
    private String title;
    private String description;
    private String priority;
    private String startDate;
    private String deadline;
    private String status;
    private String attachment;
}