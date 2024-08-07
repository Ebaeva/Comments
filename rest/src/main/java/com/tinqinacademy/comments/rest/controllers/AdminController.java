package com.tinqinacademy.comments.rest.controllers;

import com.tinqinacademy.comments.api.errors.Errors;
import com.tinqinacademy.comments.api.operation.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comments.api.operation.deletecomment.DeleteCommentOperation;
import com.tinqinacademy.comments.api.operation.putcomment.PutCommentInput;
import com.tinqinacademy.comments.api.operation.putcomment.PutCommentOperation;
import com.tinqinacademy.comments.api.operation.putcomment.PutCommentOutput;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import com.tinqinacademy.comments.api.operation.deletecomment.DeleteCommentOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController extends BaseController {
    private final DeleteCommentOperation deleteCommentOperation;
    private final PutCommentOperation putCommentOperation;

    public AdminController(DeleteCommentOperation deleteCommentOperation, PutCommentOperation putCommentOperation) {
        this.deleteCommentOperation = deleteCommentOperation;
        this.putCommentOperation = putCommentOperation;
    }


    @PutMapping(URLMappings.PUT_COMMENT)
    public ResponseEntity<?> putComment(@RequestBody @Valid PutCommentInput input, @PathVariable String commentId){
        input.setId(commentId);

        Either<Errors, PutCommentOutput> result= putCommentOperation.process(input);
        return handleResult(result);

    }

    @DeleteMapping(URLMappings.DELETE_COMMENT)
    public ResponseEntity<?> deleteComment(@PathVariable String commentId){
        DeleteCommentInput input = DeleteCommentInput
                .builder()
                .commentId(commentId)
                .build();

        Either<Errors, DeleteCommentOutput> result = deleteCommentOperation.process(input);
        return handleResult(result);
    }
}
