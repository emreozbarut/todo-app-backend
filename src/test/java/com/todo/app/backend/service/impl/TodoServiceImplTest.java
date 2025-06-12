package com.todo.app.backend.service.impl;

import com.todo.app.backend.dto.CreateTodoRequest;
import com.todo.app.backend.dto.TodoDto;
import com.todo.app.backend.exception.NotAuthorizedException;
import com.todo.app.backend.exception.ResourceNotFoundException;
import com.todo.app.backend.model.Todo;
import com.todo.app.backend.model.UserDetail;
import com.todo.app.backend.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private UserDetail testUser;
    private Todo testTodo;
    private TodoDto testTodoDto;
    private CreateTodoRequest createRequest;

    @BeforeEach
    void setUp() {
        testUser = UserDetail.builder()
                .id("user1")
                .email("test@example.com")
                .build();

        testTodo = Todo.builder()
                .id("todo1")
                .title("Test Todo")
                .description("Test Description")
                .completed(false)
                .userId(testUser.getId())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        testTodoDto = TodoDto.builder()
                .id(testTodo.getId())
                .title(testTodo.getTitle())
                .description(testTodo.getDescription())
                .completed(testTodo.isCompleted())
                .createdAt(testTodo.getCreatedAt())
                .updatedAt(testTodo.getUpdatedAt())
                .build();

        createRequest = CreateTodoRequest.builder()
                .title("New Todo")
                .description("New Description")
                .build();
    }

    @Test
    void createTodo_Success() {
        // Given
        when(todoRepository.save(any(Todo.class))).thenReturn(testTodo);

        // When
        TodoDto result = todoService.createTodo(createRequest, testUser);

        // Then
        assertNotNull(result);
        assertEquals(testTodoDto.getTitle(), result.getTitle());
        verify(todoRepository).save(any(Todo.class));
    }

    @Test
    void getTodoById_Success() {
        // Given
        when(todoRepository.findById(testTodo.getId())).thenReturn(Optional.of(testTodo));

        // When
        TodoDto result = todoService.getTodoById(testTodo.getId(), testUser);

        // Then
        assertNotNull(result);
        assertEquals(testTodoDto.getTitle(), result.getTitle());
        verify(todoRepository).findById(testTodo.getId());
    }

    @Test
    void getTodoById_NotFound() {
        // Given
        when(todoRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            todoService.getTodoById("nonexistent", testUser);
        });

        verify(todoRepository).findById("nonexistent");
    }

    @Test
    void getTodoById_NotAuthorized() {
        // Given
        Todo otherUserTodo = Todo.builder()
                .id("todo2")
                .userId("other-user")
                .build();
        when(todoRepository.findById("todo2")).thenReturn(Optional.of(otherUserTodo));

        // When & Then
        assertThrows(NotAuthorizedException.class, () -> {
            todoService.getTodoById("todo2", testUser);
        });

        verify(todoRepository).findById("todo2");
    }

    @Test
    void getAllTodos_Success() {
        // Given
        when(todoRepository.findByUserIdAndIsActive(testUser.getId(), true))
                .thenReturn(Collections.singletonList(testTodo));

        // When
        List<TodoDto> result = todoService.getAllTodos(testUser, true);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTodoDto.getTitle(), result.get(0).getTitle());
        verify(todoRepository).findByUserIdAndIsActive(testUser.getId(), true);
    }

    @Test
    void getTodos_Pagination() {
        // Given
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Todo> page = new PageImpl<>(Collections.singletonList(testTodo));
        when(todoRepository.findByUserIdAndIsActive(testUser.getId(), true, pageable))
                .thenReturn(page);

        // When
        Page<TodoDto> result = todoService.getTodos(pageable, testUser, true);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(testTodoDto.getTitle(), result.getContent().get(0).getTitle());
        verify(todoRepository).findByUserIdAndIsActive(testUser.getId(), true, pageable);
    }

    @Test
    void deleteTodo_Success() {
        // Given
        when(todoRepository.findById(testTodo.getId())).thenReturn(Optional.of(testTodo));

        // When
        todoService.deleteTodo(testTodo.getId(), testUser);

        // Then
        verify(todoRepository).findById(testTodo.getId());
        verify(todoRepository).delete(any(Todo.class));
    }

    @Test
    void deleteTodo_NotFound() {
        // Given
        when(todoRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> {
            todoService.deleteTodo("nonexistent", testUser);
        });

        verify(todoRepository).findById("nonexistent");
    }

    @Test
    void deleteTodo_NotAuthorized() {
        // Given
        Todo otherUserTodo = Todo.builder()
                .id("todo2")
                .userId("other-user")
                .build();
        when(todoRepository.findById("todo2")).thenReturn(Optional.of(otherUserTodo));

        // When & Then
        assertThrows(NotAuthorizedException.class, () -> {
            todoService.deleteTodo("todo2", testUser);
        });

        verify(todoRepository).findById("todo2");
    }
}