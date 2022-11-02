package com.gbossoufolly.blogapi.controllers;

import com.gbossoufolly.blogapi.payloads.ApiResponse;
import com.gbossoufolly.blogapi.payloads.CommentDTO;
import com.gbossoufolly.blogapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/add-comment/{postId}")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO comment,
                                                    @PathVariable("postId") Integer postId) {

        CommentDTO createComment =  commentService.createComment(comment, postId);

        return new ResponseEntity<CommentDTO>(createComment, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId) {

         commentService.deleteComment(commentId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted successfully", true),
                                                    HttpStatus.CREATED);
    }


}
