package com.gbossoufolly.blogapi.services.impl;

import com.gbossoufolly.blogapi.entities.Comment;
import com.gbossoufolly.blogapi.entities.Post;
import com.gbossoufolly.blogapi.exceptions.ResourceNotFoundException;
import com.gbossoufolly.blogapi.payloads.CommentDTO;
import com.gbossoufolly.blogapi.repositories.CommentRepository;
import com.gbossoufolly.blogapi.repositories.PostRepository;
import com.gbossoufolly.blogapi.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepository;

    private CommentRepository commentRepository;

    private ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(PostRepository postRepository,
                              CommentRepository commentRepository, ModelMapper modelMapper) {

        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {

        Post post = postRepository.findById(postId).orElseThrow(() ->
                    new ResourceNotFoundException("Post", "post ID", postId));

        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDTO.class);

    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                    new ResourceNotFoundException("Comment", "comment ID", commentId));

        commentRepository.delete(comment);
    }
}
