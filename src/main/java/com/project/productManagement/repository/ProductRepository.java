package com.project.productManagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.project.productManagement.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

	@Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.category =:category")
	Optional<List<Product>> findByCategory(String category);

	@Query(nativeQuery = true, value = "SELECT * FROM products p WHERE p.product_price =:productPrice")
	Optional<List<Product>> findByPrice(double productPrice);

	@Query(nativeQuery = true, value = "SELECT * FROM products p WHERE CONCAT(p.product_name) LIKE %?1%")
	Optional<List<Product>> searchProductByName(String productName);

	@Query(value = "select p from Product p WHERE Concat((p.productName),(p.category)) like %:search%")
	public Page<Product> fetchAllProductsBySearching(String search, PageRequest pageDetails);

	@Query(value = "select p from Product p")
	public Page<Product> fetchAllProducts(PageRequest pageDetails);
	
	//public List<Product> fetchAllFiltered(Specification<Product> specification);

}
