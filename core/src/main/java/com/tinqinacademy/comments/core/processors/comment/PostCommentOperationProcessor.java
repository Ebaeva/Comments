package com.tinqinacademy.comments.core.processors.comment;

import com.tinqinacademy.comments.api.errors.Errors;
import com.tinqinacademy.comments.core.errors.ErrorMapper;
import com.tinqinacademy.comments.core.exceptions.CommentNotFoundException;
import com.tinqinacademy.comments.core.exceptions.NoCommentsFoundException;
import com.tinqinacademy.comments.persistence.entities.Comment;
import com.tinqinacademy.comments.persistence.repository.CommentRepository;
import io.vavr.Predicates;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import com.tinqinacademy.comments.api.operation.postcoment.PostCommentInput;
import com.tinqinacademy.comments.api.operation.postcoment.PostCommentOperation;
import com.tinqinacademy.comments.api.operation.postcoment.PostCommentOutput;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import com.tinqinacademy.comments.core.processors.BaseOperationProcessor;

import java.util.UUID;

import static io.vavr.API.*;

@Slf4j
@Service
public class PostCommentOperationProcessor extends BaseOperationProcessor implements PostCommentOperation {
    private final CommentRepository commentRepository;

    protected PostCommentOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper, CommentRepository commentRepository) {
        super(conversionService, validator, errorMapper);
        this.commentRepository = commentRepository;
    }


    @Override
    public Either<Errors, PostCommentOutput> process(PostCommentInput input) {
        return Try.of(() -> {
                    log.info("Start postComment id: {}", input.toString());

                    Comment comment = Comment
                            .builder()
                            .authorLastName(input.getLastName())
                            .authorFirstName(input.getFirstName())
                            .content(input.getContent())
                            //TODO RoomNotFoundException
                            .roomId(UUID.fromString(input.getRoomId()))
                            .build();

                    commentRepository.save(comment);

                    PostCommentOutput result = conversionService.convert(comment, PostCommentOutput.class);

                    log.info("End postComment result: {}", result);
                    return result;
                })
                .toEither()
                .mapLeft(throwable -> (Errors) Match(throwable).of(
                        Case($(Predicates.instanceOf(CommentNotFoundException.class)), errorMapper::mapErrors),
                        Case($(Predicates.instanceOf(NoCommentsFoundException.class)), errorMapper::mapErrors),
                        Case($(), errorMapper::mapErrors)
                ));

    }
}

