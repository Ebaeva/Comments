package com.tinqinacademy.comments.api.operation.postcoment;

import com.tinqinacademy.comments.api.base.OperationInput;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PostCommentInput implements OperationInput {
    @JsonIgnore
    private String roomId;

    private String firstName;

    private String lastName;

    private String content;
}
