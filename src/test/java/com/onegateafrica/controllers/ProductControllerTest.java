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
import com.onegateafrica.entities.Product;
import com.onegateafrica.services.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private ProductService productService;
	@MockBean
	private MapValidationErrorService MapErrors;

	@Test
	void getAllCategories() throws Exception {
		Category cat1 = new Category(1L, "cat1", 10, new ArrayList<>(), new Date(), new Date());
		Product prod1 = new Product(1L, "produit 1", 10,true,cat1, new Date(), new Date());
		Product prod2 = new Product(2L, "produit 2", 10,true,cat1, new Date(), new Date());
		List<Product> listProducts = new ArrayList<Product>(Arrays.asList(prod1, prod2));
		Mockito.when(productService.findAllProducts()).thenReturn(listProducts);
		mvc.perform(get("/api/product").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].nom", is("produit 1")))
				.andExpect(jsonPath("$[1].nom", is("produit 2")));
	}
	@Test
	void getProductsByCat() throws Exception {
		Category cat1 = new Category(1L, "cat1", 10, new ArrayList<>(), new Date(), new Date());
		Product prod1 = new Product(1L, "produit 1", 10,true,cat1, new Date(), new Date());
		Product prod2 = new Product(2L, "produit 2", 10,true,cat1, new Date(), new Date());
		List<Product> listProducts = new ArrayList<Product>(Arrays.asList(prod1, prod2));
		Mockito.when(productService.findAllByCategoryProducts(1L)).thenReturn(listProducts);
		mvc.perform(get("/api/product/category/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].nom", is("produit 1")))
				.andExpect(jsonPath("$[1].nom", is("produit 2")));
	}

	@Test
	void getCatById() throws Exception {
		Category cat1 = new Category(1L, "cat1", 10, new ArrayList<>(), new Date(), new Date());
		Product prod1 = new Product(1L, "produit 1", 10,true,cat1, new Date(), new Date());
		Mockito.when(productService.findProductById(1L)).thenReturn(Optional.of(prod1));
		mvc.perform(get("/api/product/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.nom", is("produit 1"))).andExpect(jsonPath("$.id", is(1)));
	}
	@Test
	void saveCat() throws Exception {
		Category cat1 = new Category(1L, "cat1", 10, new ArrayList<>(), new Date(), new Date());
		Product prod1 = new Product(1L, "produit 1", 10,true,cat1, new Date(), new Date());
		Mockito.when(productService.saveProduct(prod1)).thenReturn(prod1);
		mvc.perform(post("/api/product")
				.content(asJsonString((prod1)))
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
