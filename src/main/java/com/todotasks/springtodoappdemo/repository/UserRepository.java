package com.todotasks.springtodoappdemo.repository;

import com.todotasks.springtodoappdemo.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findById(long id);
}
