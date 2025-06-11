package com.todo.app.backend.controller;

import com.todo.app.backend.dto.TodoDto;
import com.todo.app.backend.model.UserDetail;
import com.todo.app.backend.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
@Tag(name = "Todo", description = "APIs for user todos")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole('ROLE_USER')")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/all")
    @Operation(
            summary = "Get all todos",
            description = "Get all todos"
    )
    @ApiResponse(responseCode = "200", description = "Get all todos successful")
    public List<TodoDto> getAll(@AuthenticationPrincipal UserDetail currentUser) {
        return todoService.getAllTodos(currentUser);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get todo by id",
            description = "Get todo by id"
    )
    @ApiResponse(responseCode = "200", description = "Get todo by id successful")
    public ResponseEntity<TodoDto> getById(@PathVariable String id,
                                           @AuthenticationPrincipal UserDetail currentUser) {
        return ResponseEntity.ok(todoService.getTodoById(id, currentUser));
    }

    @PostMapping
    @Operation(
            summary = "Create todo",
            description = "Create todo"
    )
    @ApiResponse(responseCode = "200", description = "Create todo successful")
    public ResponseEntity<TodoDto> create(@Valid @RequestBody TodoDto todoDto,
                                          @AuthenticationPrincipal UserDetail currentUser) {
        return ResponseEntity.ok(todoService.createTodo(todoDto, currentUser));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update todo",
            description = "Update todo by id"
    )
    @ApiResponse(responseCode = "200", description = "Update todo successful")
    public ResponseEntity<TodoDto> update(@PathVariable String id,
                                          @Valid @RequestBody TodoDto todoDto,
                                          @AuthenticationPrincipal UserDetail currentUser) {
        return ResponseEntity.ok(todoService.updateTodo(id, todoDto, currentUser));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete todo",
            description = "Delete todo by id"
    )
    @ApiResponse(responseCode = "200", description = "Delete todo successful")
    public ResponseEntity<?> delete(@PathVariable String id,
                                    @AuthenticationPrincipal UserDetail currentUser) {
        todoService.deleteTodo(id, currentUser);
        return ResponseEntity.ok().build();
    }
}
