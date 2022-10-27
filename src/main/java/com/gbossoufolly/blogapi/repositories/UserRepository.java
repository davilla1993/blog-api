package com.gbossoufolly.blogapi.repositories;

import com.gbossoufolly.blogapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
