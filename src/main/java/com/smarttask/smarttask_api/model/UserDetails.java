package com.smarttask.smarttask_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(length = 20)
    private String username;
    private String name;
    private String email;
    private String gender;
    private String contact;
    private String department;
    private String designation;
    private String address;
    private Integer status;
    @Transient
    private String password;
}
