package com.onegateafrica.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.onegateafrica.entities.Category;
import com.onegateafrica.entities.Product;
import com.onegateafrica.repositories.CategoryRepo;
import com.onegateafrica.repositories.ProductRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

	private final ProductRepo productRepo;
	private final CategoryRepo categoryRepo;

	public List<Product> findAllProducts() {
		return productRepo.findAll();
	}

	public List<Product> findAllByCategoryProducts(long id) {
		return productRepo.findAllByCategoryId(id);
	}

	public Optional<Product> findProductById(long id) {
		return productRepo.findById(id);
	}

	public Product saveProduct(Product product) {
		final Product prod = productRepo.save(product);
		final Category cat = categoryRepo.findById(product.getCategory().getId())
				.orElseThrow(EntityNotFoundException::new);
		cat.addProduct(prod);
		categoryRepo.save(cat);
		return prod;
	}

	public void deleteProduct(long id) {
		productRepo.deleteById(id);
	}

	public Product saveEdited(Product product) {
		return productRepo.save(product);
	}
}
