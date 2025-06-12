package com.todo.app.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTodoRequest {

    private String title;
    private String description;
    private Boolean completed;

}
