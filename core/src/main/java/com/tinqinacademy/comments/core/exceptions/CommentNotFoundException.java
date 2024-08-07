package com.tinqinacademy.comments.core.exceptions;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends BaseException {
    public CommentNotFoundException(String id) {
        super(String.format("Comment with id: %s not found", id), HttpStatus.NOT_FOUND);
    }
}