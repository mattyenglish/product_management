package com.project.productManagement.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.productManagement.model.Product;
import com.project.productManagement.repository.ProductRepository;
import com.project.productManagement.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService pdService;
	@Autowired
	private ProductRepository pdRepo;

	

	// get all products
	@GetMapping("/allProducts")
	public List<Product> getAllProducts() {
		return pdService.getAllProducts();
	}

	// get products by id
	@GetMapping("/id/{id}")
	public ResponseEntity<Product> getProductsById(@PathVariable int id) {
		Optional<Product> product = pdService.getProductById(id);
		// String message = "Product with " + id + " is not found";
		if (product.isPresent()) {
			return ResponseEntity.ok(product.get());
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// get products by category
	@GetMapping("/category/{category}")
	public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
		System.err.println(category);
		Optional<List<Product>> products = pdRepo.findByCategory(category);
		System.out.println(products);
		// String message = "Product with " + id + " is not found";
		if (products.isPresent()) {
			return ResponseEntity.ok(products.get());
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// get products by price
	@GetMapping("/price/{price}")
	public ResponseEntity<List<Product>> getProductsByPrice(@PathVariable double price) {
		Optional<List<Product>> products = pdRepo.findByPrice(price);
		if (products.isPresent()) {
			return ResponseEntity.ok(products.get());

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	// create/save product
	@PostMapping("/save")
	public String saveProduct(@RequestBody Product product) {

		pdService.save(product);
		return "Product Saved Successfully!";
	}

	// update product
	@PutMapping("/update/id/{id}")
	public ResponseEntity<String> updateProductById(@PathVariable int id, @RequestBody Product newProduct) {
		Product existingProduct = pdService.getProductById(id).orElse(null);
		if (existingProduct != null) {
			existingProduct
					.setProductName(newProduct.getProductName() != null && !newProduct.getProductName().equals("")
							? newProduct.getProductName()
							: existingProduct.getProductName());
			existingProduct.setProductPrice(newProduct.getProductPrice());
			existingProduct.setCategory(
					newProduct.getCategory() != null && !newProduct.getCategory().equals("") ? newProduct.getCategory()
							: existingProduct.getCategory());
			pdService.save(newProduct);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// delete product
	@DeleteMapping("/delete/id/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable int id) {
		Optional<Product> productId = pdService.getProductById(id);
		String message = "Product deleted Successfully";
		if (productId.isPresent()) {
			pdService.deleteProductById(id);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(message);

	}

	// delete All products
	@DeleteMapping("/delete/all")
	public ResponseEntity<String> deleteAllProducts() {

		List<Product> products = pdService.getAllProducts();
		String message = "All Product deleted Successfully";

		if (products != null) {
			pdService.deleteAllProducts();
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(message);

	}

	// pagination and sorting
	@GetMapping
	public ResponseEntity<?> getAllProducts(

			@RequestParam(value = "order", defaultValue = "asc", required = false) String order,
			@RequestParam(value = "sortBy", defaultValue = "category", required = false) String sortBy,
			@RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "productName", required = false) String productName,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "productPrice", required = false) Double productPrice,
			@RequestParam(value = "searching", required = false) String searching) {

		// defining sorting order
		Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

		// creating sort by (on parameter basis)

		Sort sort = Sort.by(direction, sortBy);

		// adding these sorting objects to PageRequest

		PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> products = pdService.getAllProducts(searching, pageRequest, productName, productPrice, category);

		// check if the particular page exist
		if (products.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Content Found!!!");
		} else {
			return ResponseEntity.ok(products);
		}

	}

}
