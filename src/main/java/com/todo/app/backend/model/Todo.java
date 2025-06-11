package com.todo.app.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Getter
@Setter
@Document
public class Todo extends BaseDocument {

    @Field
    @NotBlank
    @Size(max = 100)
    private String title;

    @Field
    @Size(max = 500)
    private String description;

    @Field
    private boolean completed = false;

    @Field
    private String userId;

}

