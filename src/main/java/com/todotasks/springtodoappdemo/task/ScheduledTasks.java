package com.todotasks.springtodoappdemo.task;

import com.todotasks.springtodoappdemo.service.TodoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final TodoService todoService;

    public ScheduledTasks(TodoService todoService) {
        this.todoService = todoService;
    }

    @Scheduled(fixedRate = 60 * 60 * 1000) // Run every 1 hour
    public void updateTodoStatus() {
        todoService.updateTodoStatus();
    }
}
