package com.example.taskmanager;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // Import List

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // New method to find all tasks for a given user
    List<Task> findByUser(User user);
}