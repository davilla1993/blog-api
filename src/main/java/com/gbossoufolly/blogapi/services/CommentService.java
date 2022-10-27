package com.gbossoufolly.blogapi.services;

import com.gbossoufolly.blogapi.payloads.CommentDTO;

public interface CommentService {

    CommentDTO createComment(CommentDTO commentDTO, Integer postId);

    void deleteComment(Integer commentId);
}
