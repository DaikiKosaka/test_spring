package jp.co.sss.test_spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.test_spring.entity.Review;
import jp.co.sss.test_spring.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void saveReview(Long productId, Review review) {
        // productIdをセットしてからレビューを保存
        review.setProductId(productId);
        reviewRepository.save(review);
    }

    public List<Review> findReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}
