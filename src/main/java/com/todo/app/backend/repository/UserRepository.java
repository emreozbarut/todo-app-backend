package com.todo.app.backend.repository;

import com.todo.app.backend.model.UserDetail;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.Optional;

public interface UserRepository extends CouchbaseRepository<UserDetail, String> {
    Optional<UserDetail> findByUsername(String username);
    Optional<UserDetail> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
