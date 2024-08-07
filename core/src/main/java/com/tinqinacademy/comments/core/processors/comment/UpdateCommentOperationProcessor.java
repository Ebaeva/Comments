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
import com.tinqinacademy.comments.api.operation.updatecomment.UpdateCommentInput;
import com.tinqinacademy.comments.api.operation.updatecomment.UpdateCommentOperation;
import com.tinqinacademy.comments.api.operation.updatecomment.UpdateCommentOutput;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import com.tinqinacademy.comments.core.processors.BaseOperationProcessor;

import java.util.UUID;

import static io.vavr.API.*;

@Service
@Slf4j
public class UpdateCommentOperationProcessor extends BaseOperationProcessor implements UpdateCommentOperation {
    private final CommentRepository commentRepository;


    protected UpdateCommentOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper, CommentRepository commentRepository) {
        super(conversionService, validator, errorMapper);
        this.commentRepository = commentRepository;

    }

    @Override
    public Either<Errors, UpdateCommentOutput> process(UpdateCommentInput input) {
        return Try.of(() -> {
                    log.info("Start updateComment id: {}", input);

                    Comment existingComment = commentRepository
                            .findById(UUID.fromString(input.getId()))
                            .orElseThrow(() -> new CommentNotFoundException(input.getId()));

                    existingComment.setContent(input.getContent());

                    commentRepository.save(existingComment);


                    UpdateCommentOutput result = UpdateCommentOutput
                            .builder()
                            .id(input.getId())
                            .build();

                    log.info("End updateComment result: {}", result.toString());
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
