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
import com.tinqinacademy.comments.api.operation.putcomment.PutCommentInput;
import com.tinqinacademy.comments.api.operation.putcomment.PutCommentOperation;
import com.tinqinacademy.comments.api.operation.putcomment.PutCommentOutput;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import com.tinqinacademy.comments.core.processors.BaseOperationProcessor;

import java.util.UUID;

import static io.vavr.API.*;

@Service
@Slf4j
public class PutCommentOperationProcessor extends BaseOperationProcessor implements PutCommentOperation {
    private final CommentRepository commentRepository;

    protected PutCommentOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper, CommentRepository commentRepository) {
        super(conversionService, validator, errorMapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<Errors, PutCommentOutput> process(PutCommentInput input) {
        return Try.of(() -> {
                    log.info("Start putComment id: {}", input);

                    commentRepository
                            .findById(UUID.fromString(input.getId()))
                            .orElseThrow(() -> new CommentNotFoundException(input.getId()));

                    Comment save = Comment
                            .builder()
                            .id(UUID.fromString(input.getId()))
                            .content(input.getContent())

                            .roomId(UUID.fromString(input.getRoomId()))
                            .authorFirstName(input.getFirstName())
                            .authorLastName(input.getLastName())
                            .build();

                    commentRepository.save(save);


                    PutCommentOutput result = PutCommentOutput
                            .builder()
                            .id(input.getId())
                            .build();

                    log.info("End putComment result: {}", result.toString());
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
