package com.tinqinacademy.comments.api.operation.postcoment;

import com.tinqinacademy.comments.api.base.OperationOutput;
import lombok.*;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PostCommentOutput implements OperationOutput {
    private String id;

    private String firstName;

    private String lastName;

    private String content;
}
