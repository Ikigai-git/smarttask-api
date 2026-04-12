package com.smarttask.smarttask_api.repository;

import com.smarttask.smarttask_api.model.TaskUpdates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskUpdatesRepository extends JpaRepository<TaskUpdates, Integer> {
    List<TaskUpdates> findByTaskId(Integer taskId);
}