package com.onegateafrica;

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
import com.onegateafrica.entities.Product;
import com.onegateafrica.repositories.CategoryRepo;
import com.onegateafrica.repositories.ProductRepo;
import com.onegateafrica.services.ProductService;



@ExtendWith(MockitoExtension.class)
class ProductServiceTests {

	@Mock
	private ProductRepo productRepo;
	@Mock
	private CategoryRepo categoryRepo;
	@InjectMocks
	private ProductService productService;

	@Test
	void addProductTest() {
		Category cat = new Category(1L,"cat1",10,new ArrayList<>(),new Date(),new Date());
		Product product = new Product(1L,"prod1",10,true,cat,new Date(),new Date());
		when(productRepo.save(any())).then(returnsFirstArg());
		when(categoryRepo.findById(product.getCategory().getId())).thenReturn(Optional.of(cat));
		when(categoryRepo.save(any())).then(returnsFirstArg());
		product = productService.saveProduct(product);
		assertThat(product.getId()).isEqualTo(1);
		assertThat(product.getNom()).isEqualTo("prod1");
		assertThat(cat.getListProd().size()).isGreaterThanOrEqualTo(1);
	}
	
	@Test
	void findAllProductsTest() {
		Category cat = new Category(1L,"cat1",10,new ArrayList<>(),new Date(),new Date());
		Product product = new Product(1L,"prod1",10,true,cat,new Date(),new Date());
		List<Product> listProd = Arrays.asList(product,new Product());
		when(productRepo.findAll()).thenReturn(listProd);
		List<Product> resulat = productService.findAllProducts();
		assertThat(resulat.size()).isGreaterThan(1);
		assertThat(resulat.get(0).getId()).isEqualTo(1);
	}
	@Test
	void findProductsByCatTest() {
		Category cat = new Category(1L,"cat1",10,new ArrayList<>(),new Date(),new Date());
		Product p1 = new Product(1L,"prod1",10,true,cat,new Date(),new Date());
		Product p2 = new Product(2L,"prod1",10,true,cat,new Date(),new Date());
		List<Product> listProd = Arrays.asList(p1,p2);
		when(productRepo.findAllByCategoryId(1L)).thenReturn(listProd);
		List<Product> resulat = productService.findAllByCategoryProducts(1L);
		assertThat(resulat.size()).isGreaterThan(1);
		assertThat(resulat.get(0).getId()).isEqualTo(1);
		assertThat(resulat.get(1).getCategory().getId()).isEqualTo(resulat.get(1).getCategory().getId());
	}
	@Test
	void findProductByIdTest() {
		Category cat = new Category(1L,"cat1",10,new ArrayList<>(),new Date(),new Date());
		Product product = new Product(1L,"prod1",10,true,cat,new Date(),new Date());
		when(productRepo.findById(any())).thenReturn(Optional.of(product));
		Product prod = productService.findProductById(1).get();
		assertThat(prod.getId()).isEqualTo(1);
		assertThat(prod.getNom()).isEqualTo("prod1");
		assertThat(prod.getCategory().getId()).isEqualTo(1L);
	}

}
