package com.todo.app.backend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Getter
@Setter
@Document
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Todo extends BaseDocument {

    @Field
    @NotBlank
    @Size(max = 100)
    private String title;

    @Field
    @Size(max = 500)
    private String description;

    @Field
    @Builder.Default
    private boolean completed = false;

    @Field
    private String userId;

}

