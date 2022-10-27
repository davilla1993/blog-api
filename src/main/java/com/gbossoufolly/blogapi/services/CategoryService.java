package com.gbossoufolly.blogapi.services;

import com.gbossoufolly.blogapi.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

    void deleteCategory(Integer categoryId);

    CategoryDTO getCategory(Integer categoryId);

    List<CategoryDTO> getAllCategories();
}
