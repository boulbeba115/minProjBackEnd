package com.onegateafrica.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.onegateafrica.entities.Category;
import com.onegateafrica.repositories.CategoryRepo;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock
	private CategoryRepo categoryRepo;
	@InjectMocks
	private CategoryService categoryService;

	@Test
	void addCategoryTest() {
		Category cat = new Category(1L,"cat1",10,new ArrayList<>(),new Date(),new Date());
		when(categoryRepo.save(any())).then(returnsFirstArg());
		Category category = categoryService.saveCategory(cat);
		assertThat(category.getId()).isEqualTo(1);
		assertThat(category.getNom()).isEqualTo("cat1");
		assertThat(category.getQte()).isGreaterThan(0);
	}
	
	@Test
	void findAllCategoriesTest() {
		Category cat = new Category(1L,"cat1",10,new ArrayList<>(),new Date(),new Date());
		Category cat2 = new Category(2L,"cat2",10,new ArrayList<>(),new Date(),new Date());
		List<Category> listCategories = Arrays.asList(cat,cat2);
		when(categoryRepo.findAll()).thenReturn(listCategories);
		List<Category> resulat = categoryService.findAllCategory();
		assertThat(resulat.size()).isGreaterThan(1);
		assertThat(resulat.get(0).getId()).isEqualTo(1);
	}
	
	@Test
	void findCategoryByIdTest() {
		Category cat = new Category(1L,"cat1",10,new ArrayList<>(),new Date(),new Date());
		when(categoryRepo.findById(any())).thenReturn(Optional.of(cat));
		Category category = categoryService.findCategoryById(1L).get();
		assertThat(category.getId()).isEqualTo(1);
		assertThat(category.getNom()).isEqualTo("cat1");
	}

}
