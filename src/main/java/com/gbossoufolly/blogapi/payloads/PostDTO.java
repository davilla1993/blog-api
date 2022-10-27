package com.gbossoufolly.blogapi.payloads;

import com.gbossoufolly.blogapi.entities.Category;
import com.gbossoufolly.blogapi.entities.Comment;
import com.gbossoufolly.blogapi.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private Integer postId;

    private String title;

    private String content;

    private String imageName;

    private Date addedDate;

    private CategoryDTO category;

    private UserDTO user;

    private Set<CommentDTO> comments = new HashSet<>();


}
