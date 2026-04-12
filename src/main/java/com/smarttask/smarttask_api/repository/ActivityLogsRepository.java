package com.smarttask.smarttask_api.repository;

import com.smarttask.smarttask_api.model.ActivityLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityLogsRepository extends JpaRepository<ActivityLogs, Integer> {
    List<ActivityLogs> findByUsername(String username);
}