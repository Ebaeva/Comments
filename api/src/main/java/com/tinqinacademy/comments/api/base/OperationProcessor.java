package com.tinqinacademy.comments.api.base;


import com.tinqinacademy.comments.api.errors.Errors;
import io.vavr.control.Either;

public interface OperationProcessor<O extends OperationOutput, I extends OperationInput> {
    Either<Errors, O> process(I input);
}
