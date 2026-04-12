package com.smarttask.smarttask_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "task_updates")
public class TaskUpdates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer updateId;
    private Integer taskId;
    private String employeeUsername;
    private String updateMessage;
    private String updateDate;
    private String filePath;
}