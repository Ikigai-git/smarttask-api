package com.smarttask.smarttask_api.repository;

import com.smarttask.smarttask_api.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {
    List<Tasks> findByManagerUsername(String managerUsername);

    List<Tasks> findByEmployeeUsername(String employeeUsername);

    List<Tasks> findByStatus(String status);

    List<Tasks> findByManagerUsernameAndStatus(String managerUsername, String status);

    List<Tasks> findByEmployeeUsernameAndStatus(String employeeUsername, String status);
}