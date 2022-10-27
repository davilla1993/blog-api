package com.gbossoufolly.blogapi.services.impl;

import com.gbossoufolly.blogapi.entities.Category;
import com.gbossoufolly.blogapi.exceptions.ResourceNotFoundException;
import com.gbossoufolly.blogapi.payloads.CategoryDTO;
import com.gbossoufolly.blogapi.repositories.CategoryRepository;
import com.gbossoufolly.blogapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category addedCategory = categoryRepository.save(category);

        return modelMapper.map(addedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category ", "Category Id", categoryId
                ));

        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());

        Category updatedCategory =  categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Category", "category id", categoryId
                ));

        categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException(
                        "Category", "category id", categoryId
                ));

        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS =  categories.stream().map((cat)->
                modelMapper.map(cat, CategoryDTO.class))
                    .collect(Collectors.toList());

        return categoryDTOS;
    }
}
