package com.gbossoufolly.blogapi.repositories;

import com.gbossoufolly.blogapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
