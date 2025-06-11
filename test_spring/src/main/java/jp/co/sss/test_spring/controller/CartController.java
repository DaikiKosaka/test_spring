package jp.co.sss.test_spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.entity.Cart;
import jp.co.sss.test_spring.entity.Product;
import jp.co.sss.test_spring.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // カート画面を表示
    @GetMapping
    public String showCart(HttpSession session, Model model) {
        model.addAttribute("cartProducts", cartService.getCart(session));
        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");
        
        int totalPrice = 0;
        if (cartList != null) {
            for (Cart cart : cartList) {
                Product product = cart.getProduct();
                int price = product.getPrice();
                int quantity = cart.getQuantity();

                totalPrice += price * quantity;
            }
        }
        model.addAttribute("cartProducts", cartList);
        model.addAttribute("totalPrice", totalPrice);
        
        return "cart";
    }

    // 商品をカートに追加
    @PostMapping("/{productId}/add-to-cart")
    public String addProductToCart(@PathVariable Long productId, @RequestParam(defaultValue = "1") int quantity, HttpSession session) {
        try {
            cartService.addToCart(productId, quantity, session);
        } catch (Exception e) {
            return "redirect:/cart?error=true";
        }
        return "redirect:/cart";
    }

    // 購入フォームを表示
    @GetMapping("/purchase")
    public String showPurchaseForm(HttpSession session, Model model) {
        model.addAttribute("cartProducts", cartService.getCart(session));
        return "purchase_form";
    }

    // カート詳細ページを表示
    @GetMapping("/cart_detail")
    public String showCartDetail(HttpSession session, Model model) {
        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");

        int totalPrice = 0;
        if (cartList != null) {
            for (Cart cart : cartList) {
                Product product = cart.getProduct();
                int price = product.getPrice();
                int quantity = cart.getQuantity();

                totalPrice += price * quantity;
            }
        }

        model.addAttribute("cartProducts", cartList);
        model.addAttribute("totalPrice", totalPrice);

        return "cart/cart_detail";
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long cartId, HttpSession session) {
        cartService.removeFromCartByCartId(cartId, session);
        return ResponseEntity.ok("削除成功");
    }

}
