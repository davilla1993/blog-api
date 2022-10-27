package com.gbossoufolly.blogapi.repositories;

import com.gbossoufolly.blogapi.entities.Category;
import com.gbossoufolly.blogapi.entities.Post;
import com.gbossoufolly.blogapi.entities.User;
import com.gbossoufolly.blogapi.payloads.PostDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByCategory(Category category);
    List<Post> findByUser(User user);

    List<Post> findByTitleContainingIgnoreCase(String keyword);
}
