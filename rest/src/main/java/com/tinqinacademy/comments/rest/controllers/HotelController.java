package com.tinqinacademy.comments.rest.controllers;


import com.tinqinacademy.comments.api.errors.Errors;
import com.tinqinacademy.comments.api.operation.getcommentbyroom.GetCommentsByRoomInput;
import com.tinqinacademy.comments.api.operation.getcommentbyroom.GetCommentsByRoomOperation;
import com.tinqinacademy.comments.api.operation.getcommentbyroom.GetCommentsByRoomOutput;
import com.tinqinacademy.comments.api.operation.postcoment.PostCommentInput;
import com.tinqinacademy.comments.api.operation.postcoment.PostCommentOperation;
import com.tinqinacademy.comments.api.operation.postcoment.PostCommentOutput;
import com.tinqinacademy.comments.api.operation.updatecomment.UpdateCommentInput;
import com.tinqinacademy.comments.api.operation.updatecomment.UpdateCommentOperation;
import com.tinqinacademy.comments.api.operation.updatecomment.UpdateCommentOutput;
import io.vavr.control.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HotelController extends BaseController {

    private final PostCommentOperation postCommentOperation;
    private final UpdateCommentOperation updateCommentOperation;
    private final GetCommentsByRoomOperation getCommentsByRoomOperation;

    public HotelController(PostCommentOperation postCommentOperation, UpdateCommentOperation updateCommentOperation, GetCommentsByRoomOperation getCommentsByRoomOperation) {
        this.postCommentOperation = postCommentOperation;
        this.updateCommentOperation = updateCommentOperation;
        this.getCommentsByRoomOperation = getCommentsByRoomOperation;
    }


    @GetMapping(URLMappings.GET_COMMENTS_BY_ROOM_ID)
    public ResponseEntity<?> getCommentsByRoom(@PathVariable String roomId) {
        GetCommentsByRoomInput input = GetCommentsByRoomInput.builder().id(roomId).build();

        Either<Errors, GetCommentsByRoomOutput> result = getCommentsByRoomOperation.process(input);
        return handleResult(result);
    }

    @PostMapping(URLMappings.POST_COMMENT)
    public ResponseEntity<?> postComment(@PathVariable String roomId, @RequestBody PostCommentInput input) {
        input.setRoomId(roomId);

        Either<Errors, PostCommentOutput> result = postCommentOperation.process(input);
        return handleResult(result);
    }

    @PatchMapping(URLMappings.PATCH_COMMENT)
    public ResponseEntity<?> updateComment(@PathVariable String commentId) {
        UpdateCommentInput input = UpdateCommentInput.builder().id(commentId).build();
        Either<Errors, UpdateCommentOutput> result = updateCommentOperation.process(input);
        return handleResult(result);
    }
}
