package com.gbossoufolly.blogapi.repositories;

import com.gbossoufolly.blogapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
