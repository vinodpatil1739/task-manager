package com.example.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getAllTasksForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByUser(user);
    }

    public Task createTaskForUser(Task task, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    public void deleteTaskForUser(Long id, String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("User not authorized to delete this task");
        }
        taskRepository.deleteById(id);
    }

    public Task updateTaskForUser(Long id, Task taskDetails, String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        if (!task.getUser().getUsername().equals(username)) {
            throw new RuntimeException("User not authorized to update this task");
        }
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());
        task.setTaskType(taskDetails.getTaskType());
        task.setDueDate(taskDetails.getDueDate());
        return taskRepository.save(task);
    }
}