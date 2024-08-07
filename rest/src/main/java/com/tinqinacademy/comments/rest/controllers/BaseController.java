package com.tinqinacademy.comments.rest.controllers;

import com.tinqinacademy.comments.api.base.OperationOutput;
import com.tinqinacademy.comments.api.errors.Errors;
import com.tinqinacademy.comments.api.operation.putcomment.PutCommentOutput;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
    protected ResponseEntity<?> handleResult(Either<Errors, ? extends OperationOutput> result) {
        return result.isLeft()
                ? new ResponseEntity<>(result.getLeft(), result.getLeft().getStatus())
                : new ResponseEntity<>(result.get(), HttpStatus.OK);

    }
}
