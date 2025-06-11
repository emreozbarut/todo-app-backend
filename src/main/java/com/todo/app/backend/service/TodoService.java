package com.todo.app.backend.service;

import com.todo.app.backend.dto.TodoDto;
import com.todo.app.backend.model.UserDetail;

import java.util.List;

public interface TodoService {

    List<TodoDto> getAllTodos(UserDetail currentUser);

    TodoDto getTodoById(String id, UserDetail currentUser);

    TodoDto createTodo(TodoDto todoDto, UserDetail currentUser);

    TodoDto updateTodo(String id, TodoDto todoDto, UserDetail currentUser);

    void deleteTodo(String id, UserDetail currentUser);

}
