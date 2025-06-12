package com.todo.app.backend.service;

import com.todo.app.backend.dto.CreateTodoRequest;
import com.todo.app.backend.dto.TodoDto;
import com.todo.app.backend.dto.UpdateTodoRequest;
import com.todo.app.backend.model.UserDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TodoService {

    List<TodoDto> getAllTodos(UserDetail currentUser, boolean isActive);

    Page<TodoDto> getTodos(PageRequest pageRequest, UserDetail currentUser, boolean isActive);

    TodoDto getTodoById(String id, UserDetail currentUser);

    TodoDto createTodo(CreateTodoRequest request, UserDetail currentUser);

    TodoDto updateTodo(String id, UpdateTodoRequest request, UserDetail currentUser);

    void deleteTodo(String id, UserDetail currentUser);
}
