package jp.co.sss.test_spring.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.co.sss.test_spring.entity.Product;
import jp.co.sss.test_spring.entity.Review;
import jp.co.sss.test_spring.repository.ProductRepository;
import jp.co.sss.test_spring.repository.ReviewRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product findProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> findProductsByKeyword(String keyword) {
        return productRepository.findByProductNameContaining(keyword);
    }

    public List<Review> findReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Product> findProductsByProductId(Long productId) {
        return productRepository.findById(productId)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }

    public List<Product> findProductsByCategoryId(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Transactional
    public void updateCategoryByProductName(String productName, Integer categoryId) {
        Product product = productRepository.findByProductName(productName);
        if (product != null) {
            product.setCategoryId(categoryId);
            productRepository.save(product);
        }
    }

    @Transactional
    public void reduceStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null && product.getStock() != null && product.getStock() >= quantity) {
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
        }
    }
}
