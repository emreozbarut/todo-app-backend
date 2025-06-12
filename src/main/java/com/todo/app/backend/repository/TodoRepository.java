package com.todo.app.backend.repository;

import com.todo.app.backend.model.Todo;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TodoRepository extends CouchbaseRepository<Todo, String> {

    List<Todo> findByUserId(String userId);
    List<Todo> findByUserIdAndIsActive(String userId, boolean isActive);

    List<Todo> findByUserIdAndCompleted(String userId, boolean completed);

    Page<Todo> findByUserId(String userId, Pageable pageable);
    Page<Todo> findByUserIdAndIsActive(String userId, boolean isActive, Pageable pageable);

}
