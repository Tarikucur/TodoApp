package com.todotasks.springtodoappdemo.model;

import com.todotasks.springtodoappdemo.enums.TodoImportance;
import com.todotasks.springtodoappdemo.enums.TodoStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "todos")
public class Todo {

    @Id
    private String id;

    private String userId;
    private String title;
    private String description;

    private LocalDateTime creationDate;
    private Integer targetDays;
    private TodoStatus status;
    private TodoImportance importance;
    private String tag;

    public Todo() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getTargetDays() {
        return targetDays;
    }

    public void setTargetDays(Integer targetDays) {
        this.targetDays = targetDays;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public TodoImportance getImportance() {
        return importance;
    }

    public void setImportance(TodoImportance importance) {
        this.importance = importance;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
