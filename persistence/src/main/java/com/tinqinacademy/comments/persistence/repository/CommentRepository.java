package com.tinqinacademy.comments.persistence.repository;

import com.tinqinacademy.comments.persistence.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByRoomId(UUID roomId);
}
