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
        newCart.setCartId(System.currentTimeMillis());
        newCart.setProduct(product);
        newCart.setQuantity(quantity);
        newCart.setUserId((long) 1);
        cartList.add(newCart);

        session.setAttribute("cart", cartList);
    }

    @SuppressWarnings("unchecked")
    public List<Cart> getCart(HttpSession session) {
        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");
        if (cartList == null) {
            cartList = new ArrayList<>();
        }
        return cartList;
    }

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

    public List<Product> getCartItems(HttpSession session) {
        List<Cart> cartList = getCart(session);
        List<Product> products = new ArrayList<>();

        for (Cart cart : cartList) {
            products.add(cart.getProduct());
        }

        return products;
    }

    public double calculateCartTotal(List<Cart> cartList) {
        double total = 0;
        for (Cart cart : cartList) {
            Product product = cart.getProduct();
            int quantity = cart.getQuantity();
            total += product.getPrice() * quantity;
        }
        return total * 1.1;
    }

    public List<Cart> getCartItemsByUserId(Integer userId) {
        return null;
    }
}
