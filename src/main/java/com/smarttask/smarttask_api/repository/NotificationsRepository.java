package com.smarttask.smarttask_api.repository;

import com.smarttask.smarttask_api.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Integer> {
    List<Notifications> findBySentTo(String sentTo);

    List<Notifications> findBySentToAndStatus(String sentTo, Integer status);
}