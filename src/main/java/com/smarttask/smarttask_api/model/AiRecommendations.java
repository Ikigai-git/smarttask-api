package com.smarttask.smarttask_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ai_recommendations")
public class AiRecommendations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recId;
    private Integer taskId;
    @Column(length = 20)
    private String username;
    @Column(columnDefinition = "TEXT")
    private String recommendation;
    @Column(length = 30)
    private String createdDate;
}
