package com.gbossoufolly.blogapi.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    private Integer categoryId;

    @NotBlank
    @Size(min = 4, message = "Category title must be min of 4 characters")
    private String categoryTitle;

    @NotBlank
    @Size(min = 10, message = "Description must be min of 10 characters")
    private String categoryDescription;
}
