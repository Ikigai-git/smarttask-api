package com.smarttask.smarttask_api.repository;

import com.smarttask.smarttask_api.model.Login;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {

    List<Login> findByRole(String role);
}