package com.smarttask.smarttask_api.repository;

import com.smarttask.smarttask_api.model.UserDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
    UserDetails findByUsername(String username);

    List<UserDetails> findByUsernameIn(List<String> usernames);
}