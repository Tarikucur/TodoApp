package com.todotasks.springtodoappdemo.service;

import com.todotasks.springtodoappdemo.enums.TodoStatus;
import com.todotasks.springtodoappdemo.model.Todo;
import com.todotasks.springtodoappdemo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo getTodoById(String id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);
        return todoOptional.orElse(null);
    }

    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Todo updateTodo(String id, Todo updatedTodo) {
        Optional<Todo> existingTodoOptional = todoRepository.findById(id);

        if (existingTodoOptional.isPresent()) {
            Todo existingTodo = existingTodoOptional.get();

            existingTodo.setTitle(updatedTodo.getTitle());
            existingTodo.setDescription(updatedTodo.getDescription());
            existingTodo.setTargetDays(updatedTodo.getTargetDays());
            existingTodo.setStatus(updatedTodo.getStatus());
            existingTodo.setImportance(updatedTodo.getImportance());
            existingTodo.setTag(updatedTodo.getTag());

            return todoRepository.save(existingTodo);
        } else {
            return null;
        }
    }

    public boolean deleteTodo(String id) {
        Optional<Todo> todoOptional = todoRepository.findById(id);

        if (todoOptional.isPresent()) {
            todoRepository.delete(todoOptional.get());
            return true;
        } else {
            return false;
        }
    }

    public void updateTodoStatus() {
        List<Todo> todos = todoRepository.findAll();

        for (Todo todo : todos) {
            if (isTodoExpired(todo)) {
                todo.setStatus(TodoStatus.EXPIRED);
                todoRepository.save(todo);
            }
        }
    }

    private boolean isTodoExpired(Todo todo) {
        LocalDateTime creationDate = todo.getCreationDate();
        Integer targetDays = todo.getTargetDays();

        if (creationDate != null && targetDays != null) {
            LocalDate currentDate = LocalDate.now();
            LocalDate targetDate = creationDate.toLocalDate().plusDays(targetDays);
            return currentDate.isAfter(targetDate);
        }

        return false;
    }

    public List<Todo> getTodosByUserIdAndTags(String userId, List<String> tags) {
        List<Todo> userTodos = todoRepository.findByUserId(userId);

        if (tags != null && !tags.isEmpty()) {
            return userTodos.stream()
                    .filter(todo -> tags.contains(todo.getTag()))
                    .collect(Collectors.toList());
        }
        return userTodos;
    }

    public boolean deleteTodosByTag(String tag, String userId) {
        List<Todo> todosWithTag = todoRepository.findByTagAndUserId(tag, userId);

        if (!todosWithTag.isEmpty()) {
            todoRepository.deleteAll(todosWithTag);
            return true;
        } else {
            return false;
        }
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
}
