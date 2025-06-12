package com.todo.app.backend.service.impl;

import com.todo.app.backend.dto.CreateTodoRequest;
import com.todo.app.backend.dto.TodoDto;
import com.todo.app.backend.dto.UpdateTodoRequest;
import com.todo.app.backend.exception.NotAuthorizedException;
import com.todo.app.backend.exception.ResourceNotFoundException;
import com.todo.app.backend.model.Todo;
import com.todo.app.backend.model.UserDetail;
import com.todo.app.backend.repository.TodoRepository;
import com.todo.app.backend.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private static final String TODO_NOT_FOUND_MESSAGE = "Todo not found with id: %s";

    private final TodoRepository todoRepository;

    @Override
    public List<TodoDto> getAllTodos(UserDetail currentUser, boolean isActive) {
        if (isActive) {
            return todoRepository.findByUserIdAndIsActive(currentUser.getId(), true)
                    .stream()
                    .map(this::convertToDto)
                    .toList();
        }
        return todoRepository.findByUserId(currentUser.getId())
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public Page<TodoDto> getTodos(PageRequest pageRequest, UserDetail currentUser, boolean isActive) {
        if (isActive) {
            return todoRepository.findByUserIdAndIsActive(currentUser.getId(), true, pageRequest)
                    .map(this::convertToDto);
        }
        Page<Todo> todos = todoRepository.findByUserId(currentUser.getId(), pageRequest);
        return todos.map(this::convertToDto);
    }

    @Override
    public TodoDto getTodoById(String id, UserDetail currentUser) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(TODO_NOT_FOUND_MESSAGE, id)));

        if (Objects.equals(todo.getUserId(), currentUser.getId())) {
            return convertToDto(todo);
        }
        throw new NotAuthorizedException(String.format("User not authorized to access todo with id: %s", id));
    }

    @Override
    public TodoDto createTodo(CreateTodoRequest request, UserDetail currentUser) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setUserId(currentUser.getId());

        Todo savedTodo = todoRepository.save(todo);
        return convertToDto(savedTodo);
    }

    @Override
    public TodoDto updateTodo(String id, UpdateTodoRequest request, UserDetail currentUser) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(TODO_NOT_FOUND_MESSAGE, id)));

        if (!Objects.equals(todo.getUserId(), currentUser.getId())) {
            throw new NotAuthorizedException(String.format("User not authorized to update todo with id: %s", id));
        }

        Optional.ofNullable(request.getTitle()).ifPresent(todo::setTitle);
        Optional.ofNullable(request.getDescription()).ifPresent(todo::setDescription);
        Optional.ofNullable(request.getCompleted()).ifPresent(todo::setCompleted);

        Todo updatedTodo = todoRepository.save(todo);
        return convertToDto(updatedTodo);
    }

    @Override
    public void deleteTodo(String id, UserDetail currentUser) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(TODO_NOT_FOUND_MESSAGE, id)));

        if (!Objects.equals(todo.getUserId(), currentUser.getId())) {
            throw new NotAuthorizedException(String.format("User not authorized to delete todo with id: %s", id));
        }

        todoRepository.delete(todo);
    }

    private TodoDto convertToDto(Todo todo) {
        return TodoDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.isCompleted())
                .isActive(todo.isActive())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .build();
    }

}
