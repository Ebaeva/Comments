package com.tinqinacademy.comments.core.converters;

import com.tinqinacademy.comments.api.operation.postcoment.PostCommentOutput;
import com.tinqinacademy.comments.persistence.entities.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentToPostCommentOutput implements Converter<Comment, PostCommentOutput> {
    @Override
    public PostCommentOutput convert(Comment source) {
        return PostCommentOutput
                .builder()
                .firstName(source.getAuthorFirstName())
                .lastName(source.getAuthorLastName())
                .content(source.getContent())
                .build();
    }
}