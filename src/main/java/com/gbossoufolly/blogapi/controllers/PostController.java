package com.gbossoufolly.blogapi.controllers;

import com.gbossoufolly.blogapi.config.AppConstants;
import com.gbossoufolly.blogapi.payloads.ApiResponse;
import com.gbossoufolly.blogapi.payloads.PostDTO;
import com.gbossoufolly.blogapi.payloads.PostResponse;
import com.gbossoufolly.blogapi.services.FileService;
import com.gbossoufolly.blogapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Autowired
    public PostController(PostService postService, FileService fileService) {

         this.postService = postService;
         this.fileService = fileService;
    }

    // create post
    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO,
                                              @PathVariable("userId") Integer userId,
                                              @PathVariable("categoryId") Integer categoryId) {

        PostDTO createPost = postService.createPost(postDTO, userId, categoryId);

        return new ResponseEntity<PostDTO>(createPost, HttpStatus.CREATED);
    }

    // get posts by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable("userId") Integer userId) {

        List<PostDTO> posts = postService.getPostsByUser(userId);

        return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
    }

    // get posts by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable("categoryId") Integer categoryId) {

        List<PostDTO> posts = postService.getPostsByCategory(categoryId);

        return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
    }

    // get all posts
    @GetMapping(value = {"/","/all-posts"})
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        PostResponse postResponse = postService.getAllPosts(pageNumber,pageSize, sortBy, sortDir);

        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    // get post by ID
    @GetMapping("get-post/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("postId") Integer postId) {

        PostDTO post = postService.getPostById(postId);

        return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
    }

    // delete post
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-post/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId) {

        postService.deletePost(postId);

        return new ApiResponse("Post is successfully deleted !!", true);
    }

    // update post
    @PutMapping("/update-post/{postId}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO,
                                              @PathVariable("postId") Integer postId) {

        PostDTO updatePost = postService.updatePost(postDTO, postId);

        return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);

    }

    // search
    @GetMapping("/search-post/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPostByTitle(@PathVariable("keyword") String keyword) {

        List<PostDTO> result = postService.searchPosts(keyword);

        return new ResponseEntity<List<PostDTO>>(result, HttpStatus.OK);
    }

    // post image upload
    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(
                                        @RequestParam("image")MultipartFile image,
                                        @PathVariable("postId") Integer postId) throws IOException {

        PostDTO postDTO =  postService.getPostById(postId);

        String fileName =  fileService.uploadImage(path, image);
        postDTO.setImageName(fileName);
        PostDTO updatePost =  postService.updatePost(postDTO, postId);

        return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);
    }

    // method to serve files
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response) throws IOException {

        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
}
