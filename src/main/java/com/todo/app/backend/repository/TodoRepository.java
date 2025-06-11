package com.todo.app.backend.repository;

import com.todo.app.backend.model.Todo;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

public interface TodoRepository extends CouchbaseRepository<Todo, String> {

    List<Todo> findByUserId(String userId);

    List<Todo> findByUserIdAndCompleted(String userId, boolean completed);

}
