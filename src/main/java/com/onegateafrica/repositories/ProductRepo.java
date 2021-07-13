package com.onegateafrica.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onegateafrica.entities.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

	List<Product> findAllByCategoryId(long id);

}
