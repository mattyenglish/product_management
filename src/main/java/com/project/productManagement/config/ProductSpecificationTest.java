package com.project.productManagement.config;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.project.productManagement.model.Product;

public class ProductSpecificationTest {
	

	    public static Specification<Product> productNameLike(String productName) {
	        return (root, query, criteriaBuilder) ->
	                criteriaBuilder.like(root.get("productName"), "%" + productName + "%");
	    }

	    public static Specification<Product> productPriceLessThanOrEqual(double productPrice) {
	        return (root, query, criteriaBuilder) ->
	                criteriaBuilder.equal(root.get("productPrice"), productPrice);
	    }

	    public static Specification<Product> categoryEquals(String category) {
	        return (root, query, criteriaBuilder) ->
	                criteriaBuilder.equal(root.get("category"), category);
	    }
	}


