package jp.co.sss.test_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.test_spring.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 商品名に検索キーワードを含む商品を取得
    List<Product> findByProductNameContaining(String productName);
}
