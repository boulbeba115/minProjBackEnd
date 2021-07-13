package com.onegateafrica.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onegateafrica.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Long>{

}
