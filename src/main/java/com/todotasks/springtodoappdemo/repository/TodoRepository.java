package com.todotasks.springtodoappdemo.repository;

import com.todotasks.springtodoappdemo.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TodoRepository extends MongoRepository<Todo, String> {
    List<Todo> findByUserId(String userId);
    List<Todo> findByTagAndUserId(String tag, String userId);
    void deleteByUserId(String userId);
}
