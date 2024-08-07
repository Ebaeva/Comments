package com.tinqinacademy.comments.api.operation.getcommentbyroom;

import com.tinqinacademy.comments.api.base.OperationOutput;
import lombok.*;
import com.tinqinacademy.comments.api.models.CommentOutput;

import java.util.List;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class GetCommentsByRoomOutput implements OperationOutput {
    List<CommentOutput> commentOutputs;

}

