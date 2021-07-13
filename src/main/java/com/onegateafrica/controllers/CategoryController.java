package com.onegateafrica.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onegateafrica.configs.MapValidationErrorService;
import com.onegateafrica.entities.Category;
import com.onegateafrica.services.CategoryService;

@RestController
@CrossOrigin
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MapValidationErrorService MapErrors;

	@GetMapping
	public List<Category> getAllCategory() {
		return categoryService.findAllCategory();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findCategoryById(@PathVariable long id) {
		try {
			return new ResponseEntity<Category>(categoryService.findCategoryById(id).get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping
	public ResponseEntity<?> saveCategory(@Valid @RequestBody Category category, BindingResult result) {
		ResponseEntity<?> errorMap = MapErrors.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		return new ResponseEntity<Category>(categoryService.saveCategory(category), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable long id) {
		try {
			categoryService.deleteCategory(id);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
		return null;
	}
}
