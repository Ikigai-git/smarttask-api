package com.smarttask.smarttask_api.repository;

import com.smarttask.smarttask_api.model.AiRecommendations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AiRecommendationsRepository extends JpaRepository<AiRecommendations, Integer> {
    List<AiRecommendations> findByTaskId(Integer taskId);
}