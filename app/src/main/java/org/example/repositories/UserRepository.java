package org.example.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.example.entities.UserInfo;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {
    public UserInfo findByUsername(String username);
}

// Spring automatically creates SELECT * FROM users WHERE username = ?
// Crud Repository-It is a Spring Data interface that provides basic CRUD
// operations like save, find, delete without writing SQL queries.
