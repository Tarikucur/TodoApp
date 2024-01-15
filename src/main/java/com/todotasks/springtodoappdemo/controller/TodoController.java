package com.todotasks.springtodoappdemo.controller;

import com.todotasks.springtodoappdemo.dto.TodoCreateDTO;
import com.todotasks.springtodoappdemo.enums.TodoStatus;
import com.todotasks.springtodoappdemo.model.Todo;
import com.todotasks.springtodoappdemo.model.UserEntity;
import com.todotasks.springtodoappdemo.repository.UserRepository;
import com.todotasks.springtodoappdemo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;
    private final UserRepository userRepository;

    public TodoController(TodoService todoService, UserRepository userRepository) {
        this.todoService = todoService;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> allTodos = todoService.getAllTodos();
        return new ResponseEntity<>(allTodos, HttpStatus.OK);
    }

    @GetMapping("/getUserTodos/{userId}")
    public ResponseEntity<List<Todo>> getAllTodosOfUser(@PathVariable String userId,
                                                        @RequestParam(required = false) List<String> tags) {
        List<Todo> todos = todoService.getTodosByUserIdAndTags(userId, tags);
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable String id) {
        Todo todo = todoService.getTodoById(id);
        if (todo != null) {
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal UserDetails userDetails,
                                        @RequestBody @Valid TodoCreateDTO todoCreateDTO) {
        if(todoCreateDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The todo cannot be null");
        }
        UserEntity user = userRepository.findByEmail(userDetails.getUsername());
        Todo todo = convertToTodoEntity(todoCreateDTO, user.getId());
        ResponseEntity<?> validationError = validateTodoInput(todo);
        if (validationError != null) {
            return validationError;
        }
        Todo createdTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    }

    private ResponseEntity<?> validateTodoInput(Todo todo) {
        if (todo.getTargetDays() < 0) {
            return errorResponse(HttpStatus.BAD_REQUEST, "The todo expiration day must be equal or greater than 0");
        } else if (todo.getTitle().length() <= 3) {
            return errorResponse(HttpStatus.BAD_REQUEST, "The title must be longer than 3 characters");
        } else if (todo.getTitle().length() > 100) {
            return errorResponse(HttpStatus.BAD_REQUEST, "The title length cannot exceed 100 characters");
        } else if (todo.getDescription() != null && todo.getDescription().length() > 3000) {
            return errorResponse(HttpStatus.BAD_REQUEST, "The description length cannot exceed 3000 characters");
        } else if (todo.getTag() != null) {
            if (todo.getTag().length() <= 3) {
                return errorResponse(HttpStatus.BAD_REQUEST, "The tag must have a length with longer than 3 characters if it is not empty");
            } else if (todo.getTag().length() > 100) {
                return errorResponse(HttpStatus.BAD_REQUEST, "The tag must not exceed 100 characters if it is not empty");
            }
        }
        return null;
    }

    private ResponseEntity<String> errorResponse(HttpStatus status, String errorMessage) {
        return ResponseEntity.status(status).body(errorMessage);
    }

    private Todo convertToTodoEntity(TodoCreateDTO todoCreateDTO, String userId) {
        Todo todo = new Todo();
        todo.setId(null);
        todo.setUserId(userId);
        todo.setTitle(todoCreateDTO.getTitle());
        todo.setDescription(todoCreateDTO.getDescription());
        todo.setTargetDays(todoCreateDTO.getTargetDays());
        todo.setStatus(TodoStatus.NOT_EXPIRED);
        todo.setImportance(todoCreateDTO.getImportance());
        todo.setTag(todoCreateDTO.getTag());

        todo.setCreationDate(LocalDateTime.now());

        return todo;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id,
                                           @RequestBody Todo todo) {
        Todo updatedTodo = todoService.updateTodo(id, todo);
        if (updatedTodo != null) {
            return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        boolean deleted = todoService.deleteTodo(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteByTag/{tag}")
    public ResponseEntity<Void> deleteTodosByTag(@AuthenticationPrincipal UserDetails userDetails,
                                                 @PathVariable String tag) {
        UserEntity user = userRepository.findByEmail(userDetails.getUsername());
        boolean deleted = todoService.deleteTodosByTag(tag, user.getId());
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/changeTag")
    public ResponseEntity<Void> changeTag(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody Map<String, Object> requestBody) {
        @SuppressWarnings("unchecked")
        List<String> oldTags = (List<String>) requestBody.get("oldTags");
        String newTag = (String) requestBody.get("newTag");
        UserEntity user = userRepository.findByEmail(userDetails.getUsername());
        List<Todo> userTodos = todoService.getTodosByUserIdAndTags(user.getId(), oldTags);

        if (!userTodos.isEmpty()) {
            for (Todo todo : userTodos) {
                todo.setTag(newTag);
                todoService.updateTodo(todo.getId(), todo);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
