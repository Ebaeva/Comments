package com.tinqinacademy.comments.core.processors.comment;

import com.tinqinacademy.comments.api.errors.Errors;
import com.tinqinacademy.comments.core.errors.ErrorMapper;
import com.tinqinacademy.comments.core.exceptions.CommentNotFoundException;
import com.tinqinacademy.comments.core.exceptions.NoCommentsFoundException;
import com.tinqinacademy.comments.core.processors.BaseOperationProcessor;
import com.tinqinacademy.comments.persistence.entities.Comment;
import com.tinqinacademy.comments.persistence.repository.CommentRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import com.tinqinacademy.comments.api.models.CommentOutput;
import com.tinqinacademy.comments.api.operation.getcommentbyroom.GetCommentsByRoomInput;
import com.tinqinacademy.comments.api.operation.getcommentbyroom.GetCommentsByRoomOperation;
import com.tinqinacademy.comments.api.operation.getcommentbyroom.GetCommentsByRoomOutput;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Service
@Slf4j
public class GetCommentsByRoomOperationProcessor extends BaseOperationProcessor implements GetCommentsByRoomOperation {
    private final CommentRepository commentRepository;

    protected GetCommentsByRoomOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper, CommentRepository commentRepository) {
        super(conversionService, validator, errorMapper);
        this.commentRepository = commentRepository;
    }


    @Override
    public Either<Errors, GetCommentsByRoomOutput> process(GetCommentsByRoomInput input) {
        log.info("Start getCommentByRoom input: {}", input);


        return Try.of(() -> {

                    log.info("Start getCommentByRoom input: {}", input);


                    List<Comment> comments = commentRepository.findAllByRoomId(UUID.fromString(input.getId()));
                    if (comments.isEmpty()) {
                        throw new NoCommentsFoundException(input.getId());
                    }

                    GetCommentsByRoomOutput result = createRoomOutput(comments);


                    log.info("End getCommentByRoom result: {}", result);
                    return result;
                })
                .toEither()
                .mapLeft(throwable -> (Errors) Match(throwable).of(
                        Case($(instanceOf(CommentNotFoundException.class)), errorMapper::mapErrors),
                        Case($(instanceOf(NoCommentsFoundException.class)), errorMapper::mapErrors),
                        Case($(), errorMapper::mapErrors)
                ));
    }

    private GetCommentsByRoomOutput createRoomOutput(List<Comment> comments) {
        return GetCommentsByRoomOutput
                .builder()
                .commentOutputs(comments
                        .stream()
                        .map(comment -> conversionService.convert(comment, CommentOutput.class))
                        .toList())
                .build();
    }

}
