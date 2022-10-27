package com.gbossoufolly.blogapi.controllers;

import com.gbossoufolly.blogapi.payloads.ApiResponse;
import com.gbossoufolly.blogapi.payloads.CategoryDTO;
import com.gbossoufolly.blogapi.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // create category
    @PostMapping("/create-category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createCategory = categoryService.createCategory(categoryDTO);

        return new ResponseEntity<CategoryDTO>(createCategory, HttpStatus.CREATED);
    }

    // update category
    @PutMapping("/update-category/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                      @PathVariable("categoryId") Integer categoryId) {

        CategoryDTO updatedCategory = categoryService.updateCategory(categoryDTO, categoryId);

        return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.OK);
    }

    // get category
    @GetMapping("/get-category/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("categoryId") Integer categoryId) {

        CategoryDTO categoryDTO =  categoryService.getCategory(categoryId);

        return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
    }

    // get all categories
    @GetMapping("/all-categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

        List<CategoryDTO> categories = categoryService.getAllCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // delete category
    @DeleteMapping("/delete-category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId) {

        categoryService.deleteCategory(categoryId);

        return new ResponseEntity<ApiResponse>(new ApiResponse(
                "Category is deleted successfully", true
        ), HttpStatus.OK);
    }
}
