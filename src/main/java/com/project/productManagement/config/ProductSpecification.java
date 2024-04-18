package com.project.productManagement.config;

import java.util.Map;
import org.springframework.data.jpa.domain.Specification;
import com.project.productManagement.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductSpecification implements Specification<Product> {

	public static final String PRODUCT_NAME = "productName";
	public static final String PRODUCT_PRICE = "productPrice";
	public static final String CATEGORY = "category";

	private transient Map<String, Object> filter;

	public ProductSpecification(Map<String, Object> filter) {
		super();
		this.filter = filter;
	}

	@Override
	public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		Predicate p = criteriaBuilder.conjunction();
		if (filter.getOrDefault(PRODUCT_NAME, null) != null) {
			p.getExpressions().add(criteriaBuilder.equal(root.get(PRODUCT_NAME), filter.get(PRODUCT_NAME)));
		}
		if (filter.getOrDefault(PRODUCT_PRICE, null) != null) {
			
			p.getExpressions().add(criteriaBuilder.equal(root.get(PRODUCT_PRICE),filter.get(PRODUCT_PRICE)));
		}

		if (filter.getOrDefault(CATEGORY, null) != null) {
			
			System.err.println(filter.get(CATEGORY));
			
			p.getExpressions().add(criteriaBuilder.equal(root.get(CATEGORY), filter.get(CATEGORY)));
			
			
		}
		
		return p;
	}

}
