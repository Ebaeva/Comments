package com.tinqinacademy.comments.api.operation.updatecomment;

import com.tinqinacademy.comments.api.base.OperationInput;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UpdateCommentInput implements OperationInput {
    @JsonIgnore
    private String id;

    private String content;
}