package com.example.restapi.repository;

import com.example.restapi.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    public UserEntity findByUsername(String username);
}
