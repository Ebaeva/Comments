package com.tinqinacademy.comments.api.operation.deletecomment;

import com.tinqinacademy.comments.api.base.OperationInput;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;
import lombok.*;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class DeleteCommentInput implements OperationInput {

    @NotBlank
    @UUID
    private String commentId;
}
