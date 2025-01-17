package jp.co.sss.test_spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.test_spring.entity.Cart;
import jp.co.sss.test_spring.entity.Product;
import jp.co.sss.test_spring.repository.CartRepository;
import jp.co.sss.test_spring.repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public void addToCart(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("商品が見つかりません"));

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setQuantity(quantity);

        cartRepository.save(cart);
    }

	public Object getCartItems() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
