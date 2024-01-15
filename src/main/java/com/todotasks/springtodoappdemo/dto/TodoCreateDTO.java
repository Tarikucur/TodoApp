package com.todotasks.springtodoappdemo.dto;

import com.todotasks.springtodoappdemo.enums.TodoImportance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class TodoCreateDTO implements Serializable {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Target days is required")
    private Integer targetDays;

    @NotNull(message = "Importance is required")
    private TodoImportance importance;

    private String tag;

    public TodoCreateDTO() {}

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

    public Integer getTargetDays() {
        return targetDays;
    }

    public void setTargetDays(Integer targetDays) {
        this.targetDays = targetDays;
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
