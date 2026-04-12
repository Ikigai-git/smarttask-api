package com.smarttask.smarttask_api.repository;

import com.smarttask.smarttask_api.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketsRepository extends JpaRepository<Tickets, Integer> {
    List<Tickets> findByEmployeeUsername(String employeeUsername);

    List<Tickets> findByManagerUsername(String managerUsername);

    List<Tickets> findByStatus(String status);

    List<Tickets> findByManagerUsernameAndStatus(String managerUsername, String status);

    List<Tickets> findByEmployeeUsernameAndStatus(String employeeUsername, String status);
}