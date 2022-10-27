package com.gbossoufolly.blogapi.services;

import com.gbossoufolly.blogapi.entities.Post;
import com.gbossoufolly.blogapi.payloads.PostDTO;
import com.gbossoufolly.blogapi.payloads.PostResponse;

import java.util.List;

public interface PostService {

    // create post
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);

    // update post
    PostDTO updatePost(PostDTO postDTO, Integer postId);

    // get all posts
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,
                             String sortBy,String sortDir);

    // get singlepost
    PostDTO getPostById(Integer postId);

    // get all post by category
    List<PostDTO> getPostsByCategory(Integer categoryId);

    // get all posts by user
    List<PostDTO> getPostsByUser(Integer userId);

    // search post
    List<PostDTO> searchPosts(String keyword);

    // delete post
    void deletePost(Integer postId);

}
