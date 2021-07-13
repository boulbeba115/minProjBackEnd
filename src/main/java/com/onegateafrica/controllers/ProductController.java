package com.onegateafrica.controllers;

import java.util.List;

import javax.validation.Valid;

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
import com.onegateafrica.entities.Product;
import com.onegateafrica.services.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;
	private final MapValidationErrorService MapErrors;

	@GetMapping
	public List<Product> getAllProduct() {
		return productService.findAllProducts();
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<?> getAllProductByCategory(@PathVariable long id) {
		try {
			return new ResponseEntity<List<Product>>(productService.findAllByCategoryProducts(id), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findProductById(@PathVariable long id) {
		try {
			return new ResponseEntity<Product>(productService.findProductById(id).get(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PostMapping
	public ResponseEntity<?> saveProduct(@Valid @RequestBody Product product, BindingResult result) {
		ResponseEntity<?> errorMap = MapErrors.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		return new ResponseEntity<Product>(productService.saveProduct(product), HttpStatus.CREATED);

	}

	@PostMapping("/edit")
	public ResponseEntity<?> saveEdited(@Valid @RequestBody Product product, BindingResult result) {
		ResponseEntity<?> errorMap = MapErrors.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		return new ResponseEntity<Product>(productService.saveEdited(product), HttpStatus.CREATED);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable long id) {
		try {
			productService.deleteProduct(id);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
		}
		return null;
	}
}
