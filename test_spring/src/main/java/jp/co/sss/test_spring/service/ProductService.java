package jp.co.sss.test_spring.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.co.sss.test_spring.entity.Cart;
import jp.co.sss.test_spring.entity.Product;
import jp.co.sss.test_spring.entity.Review;
import jp.co.sss.test_spring.repository.CartRepository;
import jp.co.sss.test_spring.repository.ProductRepository;
import jp.co.sss.test_spring.repository.ReviewRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final CartRepository cartRepository; // CartRepository を追加

    // コンストラクタでリポジトリを注入
    public ProductService(ProductRepository productRepository, ReviewRepository reviewRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.cartRepository = cartRepository;
    }

    // 商品の全リストを取得
    public List<Product> findAllProducts() {
        return productRepository.findAll(); // Repositoryからすべての商品を取得
    }

    // 商品IDに基づいて、商品を1件取得
    public Product findProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.orElse(null); // 存在しない場合はnullを返す
    }

    // 商品の保存
    public void saveProduct(Product product) {
        productRepository.save(product); // Repositoryに保存
    }

    // 商品名にキーワードが含まれる商品を取得
    public List<Product> findProductsByKeyword(String keyword) {
        return productRepository.findByProductNameContaining(keyword); // キーワードで検索
    }

    // 商品IDに基づいて口コミリストを取得
    public List<Review> findReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public List<Product> findProductsByProductId(Long productId) {
        // 商品IDを使って商品を検索するロジック
        return productRepository.findById(productId)
                                 .map(Collections::singletonList)  // 見つかった商品をリストとして返す
                                 .orElse(Collections.emptyList());  // 見つからなければ空のリストを返す
    }

    @Transactional
    public void addToCart(Long productId) {
        // 仮のユーザーIDを設定 (ユーザー認証がある場合は適切に取得)
        Integer userId = 1;

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));

        // カートに追加する
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProduct(product);
        cart.setQuantity(1); // 初期数量を1とする
        cartRepository.save(cart);
    }
}
