package jp.co.sss.test_spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.test_spring.entity.Cart;
import jp.co.sss.test_spring.repository.CartRepository;
import jp.co.sss.test_spring.repository.ProductRepository;

//CartService.java
@Service
public class CartService {

 @Autowired
 private ProductRepository productRepository;

 @Autowired
 private CartRepository cartRepository;

 public List<Cart> getCart() {
	return null;
     // ユーザーのカートアイテムを取得
 }

 public void addProductToCart(Long productId) {
     // 商品をカートに追加
 }
}
