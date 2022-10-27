package com.gbossoufolly.blogapi.services.impl;

import com.gbossoufolly.blogapi.entities.Category;
import com.gbossoufolly.blogapi.entities.Post;
import com.gbossoufolly.blogapi.entities.User;
import com.gbossoufolly.blogapi.exceptions.ResourceNotFoundException;
import com.gbossoufolly.blogapi.payloads.PostDTO;
import com.gbossoufolly.blogapi.payloads.PostResponse;
import com.gbossoufolly.blogapi.repositories.CategoryRepository;
import com.gbossoufolly.blogapi.repositories.PostRepository;
import com.gbossoufolly.blogapi.repositories.UserRepository;
import com.gbossoufolly.blogapi.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper modelMapper;

    private UserRepository userRepository;

    private CategoryRepository categoryRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper,
                           UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User", "Id", userId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Category", "category id", categoryId));

        Post post = modelMapper.map(postDTO, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = postRepository.save(post);

        return modelMapper.map(newPost, PostDTO.class);

    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {

        Post post = postRepository.findById(postId).orElseThrow(()->
                    new ResourceNotFoundException("Post", "post ID", postId));

        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName((postDTO.getImageName()));

        Post updatedPost = postRepository.save(post);

        return modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,
                                    String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc"))
                    ?Sort.by(sortBy).ascending()
                        :Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> pagePost = postRepository.findAll(p);
        List<Post> allPosts = pagePost.getContent();

        List<PostDTO> postDTOS =  allPosts.stream()
                .map((post)-> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Integer postId) {

        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post", "post ID", postId));

        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getPostsByCategory(Integer categoryId) {

        Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(()->
                                new ResourceNotFoundException("Category", "category ID", categoryId));

        List<Post> posts = postRepository.findByCategory(category);

        List<PostDTO> postDTOS = posts.stream()
                            .map((post) -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());


        return postDTOS;
    }

    @Override
    public List<PostDTO> getPostsByUser(Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "user ID", userId));

        List<Post> posts = postRepository.findByUser(user);

        List<PostDTO> postDTOS = posts.stream()
                    .map((post)-> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        return postDTOS;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {

        List<Post> posts = postRepository.findByTitleContainingIgnoreCase(keyword);
        List<PostDTO> postDTOS =  posts.stream()
                .map((post) -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());

        return postDTOS;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post", "post ID", postId));

        postRepository.delete(post);
    }
}
