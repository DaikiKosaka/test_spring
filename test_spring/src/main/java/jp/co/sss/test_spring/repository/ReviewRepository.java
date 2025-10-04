package jp.co.sss.test_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.test_spring.entity.Review;
import jp.co.sss.test_spring.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);
    List<Review> findByUser(User user);
}
