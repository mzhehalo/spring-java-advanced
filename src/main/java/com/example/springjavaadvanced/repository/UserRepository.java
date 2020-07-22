package com.example.springjavaadvanced.repository;

import com.example.springjavaadvanced.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);
}
