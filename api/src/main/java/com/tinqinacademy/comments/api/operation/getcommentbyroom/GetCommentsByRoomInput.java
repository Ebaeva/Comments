package com.tinqinacademy.comments.api.operation.getcommentbyroom;

import com.tinqinacademy.comments.api.base.OperationInput;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentsByRoomInput implements OperationInput {
    private String id;
}
