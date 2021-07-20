package com.onegateafrica.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onegateafrica.configs.MapValidationErrorService;
import com.onegateafrica.entities.Category;
import com.onegateafrica.services.CategoryService;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private CategoryService categoryService;
	@MockBean
	private MapValidationErrorService MapErrors;

	@Test
	void getAllCategories() throws Exception {
		Category cat1 = new Category(1L, "cat1", 10, new ArrayList<>(), new Date(), new Date());
		Category cat2 = new Category(2L, "cat2", 15, new ArrayList<>(), new Date(), new Date());
		List<Category> listCategory = new ArrayList<Category>(Arrays.asList(cat1, cat2));
		Mockito.when(categoryService.findAllCategory()).thenReturn(listCategory);
		mvc.perform(get("/api/category").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].nom", is("cat1")))
				.andExpect(jsonPath("$[1].nom", is("cat2")));
	}

	@Test
	void getCatById() throws Exception {
		Category cat1 = new Category(1L, "cat1", 10, new ArrayList<>(), new Date(), new Date());
		Mockito.when(categoryService.findCategoryById(1)).thenReturn(Optional.of(cat1));
		mvc.perform(get("/api/category/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.nom", is("cat1"))).andExpect(jsonPath("$.id", is(1)));
	}

	@Test
	void saveCat() throws Exception {
		Category cat1 = new Category(1L, "cat1", 10, new ArrayList<>(), new Date(), new Date());
		Mockito.when(categoryService.saveCategory(cat1)).thenReturn(cat1);
		mvc.perform(post("/api/category")
				.content(asJsonString((cat1)))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
