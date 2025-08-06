package com.example.taskmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.security.Principal; // Import Principal
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks(Principal principal) {
        return taskService.getAllTasksForUser(principal.getName());
    }

    @PostMapping
    public Task createTask(@RequestBody Task task, Principal principal) {
        return taskService.createTaskForUser(task, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id, Principal principal) {
        taskService.deleteTaskForUser(id, principal.getName());
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails, Principal principal) {
        return taskService.updateTaskForUser(id, taskDetails, principal.getName());
    }
}