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

    // コンストラクタで両方のリポジトリを注入
    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
    }

    // 商品の全リストを取得
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    // 商品IDに基づいて、商品を1件取得
    public Product findProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    // 商品の保存
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // 商品名にキーワードが含まれる商品を取得
    public List<Product> findProductsByKeyword(String keyword) {
        return productRepository.findByProductNameContaining(keyword);
    }

    // 商品IDに基づいて口コミリストを取得
    public List<Review> findReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    // 商品IDに基づいて商品を検索（リストで返す）
    public List<Product> findProductsByProductId(Long productId) {
        return productRepository.findById(productId)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }

    // カテゴリIDで商品を絞り込む
    public List<Product> findProductsByCategoryId(Integer categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // 商品名でカテゴリIDを更新
    @Transactional
    public void updateCategoryByProductName(String productName, Integer categoryId) {
        Product product = productRepository.findByProductName(productName);
        if (product != null) {
            product.setCategoryId(categoryId);
            productRepository.save(product);
        }
    }

    // ✅ 在庫を減らす（購入後）
    @Transactional
    public void reduceStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null && product.getStock() != null && product.getStock() >= quantity) {
            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
        }
    }

}
