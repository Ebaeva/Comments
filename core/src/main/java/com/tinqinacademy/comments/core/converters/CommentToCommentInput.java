package com.tinqinacademy.comments.core.converters;

import com.tinqinacademy.comments.api.models.CommentOutput;
import com.tinqinacademy.comments.persistence.entities.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CommentToCommentInput implements Converter<Comment, CommentOutput> {
    @Override
    public CommentOutput convert(Comment source) {
        return CommentOutput
                .builder()
                .id(source.getId())
                .authorFirstName(source.getAuthorFirstName())
                .authorLastName(source.getAuthorLastName())
                .content(source.getContent())
                .createdOn(LocalDate.from(source.getCreatedOn()))
                .lastEditedBy(source.getLastEditedBy())
                .build();
    }
}

