package jp.co.sss.test_spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.entity.Cart;
import jp.co.sss.test_spring.entity.Product;
import jp.co.sss.test_spring.repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    // カートに商品を追加
    @SuppressWarnings("unchecked")
    public void addToCart(Long productId, int quantity, HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return;

        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");
        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        for (Cart cart : cartList) {
            if (cart.getProduct().getProductId().equals(productId)) {
                cart.setQuantity(cart.getQuantity() + quantity);
                session.setAttribute("cart", cartList);
                return;
            }
        }

        Cart newCart = new Cart();
        newCart.setCartId(System.currentTimeMillis()); // 一時的にcartIdをセット
        newCart.setProduct(product);
        newCart.setQuantity(quantity);
        newCart.setUserId((long) 1); // 仮に固定
        cartList.add(newCart);

        session.setAttribute("cart", cartList);
    }

    // カート内の商品一覧を取得
    @SuppressWarnings("unchecked")
    public List<Cart> getCart(HttpSession session) {
        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        return cartList;
    }

    // cartIdで商品を削除
    @SuppressWarnings("unchecked")
    public void removeFromCartByCartId(Long cartId, HttpSession session) {
        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");

        System.out.println("=== カート内容確認 ===");
        if (cartList != null) {
            for (Cart cart : cartList) {
                System.out.println("Cart item ID: " + cart.getCartId());
            }

            cartList.removeIf(cart ->
                cart.getCartId() != null && cart.getCartId().equals(cartId)
            );

            session.setAttribute("cart", cartList);
        }
    }

    // カート内の商品リストを取得
    public List<Product> getCartItems(HttpSession session) {
        List<Cart> cartList = getCart(session);
        List<Product> products = new ArrayList<>();

        for (Cart cart : cartList) {
            products.add(cart.getProduct());
        }

        return products;
    }

    // カートの合計金額を計算（税金込み）
    public double calculateCartTotal(List<Cart> cartList) {
        double total = 0;
        for (Cart cart : cartList) {
            Product product = cart.getProduct();
            int quantity = cart.getQuantity();
            total += product.getPrice() * quantity;
        }
        return total * 1.1; // 消費税を加算
    }

    // ユーザーIDに基づくカートアイテムを取得（実装が必要）
    public List<Cart> getCartItemsByUserId(Integer userId) {
        // TODO: ユーザーIDに基づいてカートアイテムを取得する実装
        return null;
    }
}
