package jp.co.sss.test_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.test_spring.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 商品IDで口コミを検索
    List<Review> findByProductId(Long productId);
}
