package com.planner.Planner_backend.jwtSecurity.repository;


import com.planner.Planner_backend.jwtSecurity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);
}