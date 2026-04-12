package com.smarttask.smarttask_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "login")
public class Login {

    @Id
    @jakarta.persistence.Column(length = 20, nullable = false)
    private String username;
    @jakarta.persistence.Column(length = 255, nullable = false)
    private String password;
    @jakarta.persistence.Column(length = 20, nullable = false)
    private String role;
    private Integer status;
}
