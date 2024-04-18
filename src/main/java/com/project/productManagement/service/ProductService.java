package com.project.productManagement.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.project.productManagement.config.ProductSpecification;
import com.project.productManagement.model.Product;
import com.project.productManagement.repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository pdRepo;

	// get all products
	public List<Product> getAllProducts() {
		return pdRepo.findAll();
	}

	// get product by id
	public Optional<Product> getProductById(int id) {
		return pdRepo.findById(id);
	}

	// save products in db
	public void save(Product product) {
		pdRepo.save(product);
	}

	// edit / update product
	public Optional<Product> updateProductById(int id) {
		return pdRepo.findById(id);
	}

	// delete product by id
	public void deleteProductById(int id) {
		pdRepo.deleteById(id);
	}

	// delete all products
	public void deleteAllProducts() {
		pdRepo.deleteAll();
	}

	// pagination

	public Page<Product> getAllProducts(String search, PageRequest pageRequest, String productName, Double productPrice,
			String category) {

		if (Objects.nonNull(search)) {
			return pdRepo.fetchAllProductsBySearching(search, pageRequest);

		}

		else {

			System.err.println("category = " + category);

			// Filtering
			Map<String, Object> filter = new HashMap<>();
			filter.put(ProductSpecification.PRODUCT_NAME, productName);
			filter.put(ProductSpecification.CATEGORY, category);
			filter.put(ProductSpecification.PRODUCT_PRICE, productPrice);

			Specification<Product> specification = new ProductSpecification(filter);
			return pdRepo.findAll(specification, pageRequest);
			

		}
	}

	// test method
	/*public List<Product> filterProducts(String productName, Double productPrice, String category) {
		Specification<Product> specification = Specification.where(null);

		if (productName != null && !productName.isEmpty()) {
			specification = specification.and((root, query, cb) -> cb.like(root.get("productName"), "%" + productName + "%"));
		}

		if (productPrice != null) {
			specification = specification.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("productPrice"), productPrice));
		}

		if (category != null && !category.isEmpty()) {
			specification = specification.and((root, query, cb) -> cb.equal(root.get("category"), category));
		}

		return pdRepo.findAll(specification);
	}*/

}
