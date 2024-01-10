package com.todotasks.springtodoappdemo.repository;

import com.todotasks.springtodoappdemo.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
    Optional<UserEntity> findById(String id);
    boolean existsByEmail(String email);
}
