package jp.co.sss.test_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.test_spring.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameContaining(String productName);
    List<Product> findByCategoryId(Long categoryId);
    Product findByProductName(String productName);
    List<Product> findByCategoryId(Integer categoryId);
    List<Product> findByProductNameContainingAndCategoryId(String keyword, Integer categoryId);

}
