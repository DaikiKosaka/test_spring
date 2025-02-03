package jp.co.sss.test_spring.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.entity.Cart;
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

    // カート内の商品を取得
    public Cart getCart(HttpSession session) {
        return (Cart) session.getAttribute("cart");  // セッションからCartオブジェクトを取得
    }

    // 商品をカートに追加
    public void addToCart(Long productId, int quantity, HttpSession session) {
        Product product = findProductById(productId); // 商品情報を取得
        Cart cart = (Cart) session.getAttribute("cart"); // セッションからカートを取得

        if (cart == null) {
            cart = new Cart(); // カートがない場合、新規作成
        }

        cart.addItem(product, quantity); // 商品をカートに追加
        session.setAttribute("cart", cart); // セッションに保存
    }
    
    // 商品IDに基づいて商品を検索（リストで返す）
    public List<Product> findProductsByProductId(Long productId) {
        return productRepository.findById(productId)
                                 .map(Collections::singletonList)  // 見つかった商品をリストとして返す
                                 .orElse(Collections.emptyList());  // 見つからなければ空のリストを返す
    }

	public Product getProductById(Long productId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}