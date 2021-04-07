package com.bjs.webstore.domain.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bjs.webstore.domain.Product;
import com.bjs.webstore.domain.repository.ProductRepository;

@Repository	//하나의 컴포넌트, 빈을 의미함
public class InMemoryProductRepository implements ProductRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;	//mariaDB 연결과 연관된 빈들의 집합을 Autowired로 묶음

	public List<Product> getAllProducts() {
		Map<String, Object> params = new HashMap<String, Object>();
		List<Product> result = jdbcTemplate.query("SELECT * FROM products", params, new ProductMapper());
		return result;
	}

	private static final class ProductMapper implements RowMapper<Product> {
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			Product product = new Product();
			product.setProductId(rs.getString("ID"));
			product.setName(rs.getString("PROD_NAME"));
			product.setDescription(rs.getString("DESCRIPTION"));
			product.setUnitPrice(rs.getBigDecimal("UNIT_PRICE"));
			product.setManufacturer(rs.getString("MANUFACTURER"));
			product.setCategory(rs.getString("CATEGORY"));
			product.setCondition(rs.getString("PROD_CONDITION"));
			product.setUnitsInStock(rs.getLong("UNITS_IN_STOCK"));
			product.setUnitsInOrder(rs.getLong("UNITS_IN_ORDER"));
			product.setDiscontinued(rs.getBoolean("DISCONTINUED"));
			return product;
		}
	}
	
	@Override
	public void updateStock(String productId, long noOfUnits) {
		String SQL = "UPDATE PRODUCTS SET "
				+ "UNITS_IN_STOCK = :unitsInStock WHERE ID = :id";	//:의 의미 -> PreparedStatment를 의미함
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("unitsInStock", noOfUnits);	//map의 key, value
		params.put("id", productId);	//map의 key, value
		jdbcTemplate.update(SQL, params);
	}
	
	@Override
	public List<Product> getProductsByCategory(String category) {
		String SQL = "SELECT * FROM PRODUCTS " + "WHERE LCASE(CATEGORY) = :category";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("category", category.toLowerCase());
		return jdbcTemplate.query(SQL, params, new ProductMapper());
	}

	@Override
	public List<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
		String SQL = "SELECT * FROM PRODUCTS WHERE CATEGORY "
				+ "IN (:categories) AND MANUFACTURER IN (:brands)";
				return jdbcTemplate.query(SQL, filterParams, new ProductMapper());
	}
}
