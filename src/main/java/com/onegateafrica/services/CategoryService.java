package com.onegateafrica.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.onegateafrica.entities.Category;
import com.onegateafrica.repositories.CategoryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepo categoryRepo;

	public List<Category> findAllCategory() {
		return categoryRepo.findAll();
	}

	public Optional<Category> findCategoryById(long id) {
		return categoryRepo.findById(id);
	}

	public Category saveCategory(Category category) {
		return categoryRepo.save(category);
	}

	public void deleteCategory(long id) {
		categoryRepo.deleteById(id);
	}
}
