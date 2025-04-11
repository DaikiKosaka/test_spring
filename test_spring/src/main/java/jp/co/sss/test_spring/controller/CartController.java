package jp.co.sss.test_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.service.CartService;
import jp.co.sss.test_spring.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService service;

    public CartController(CartService cartService, ProductService service) {
        this.cartService = cartService;
        this.service = service;
    }

    @GetMapping
    public String showCart(HttpSession session, Model model) {
        model.addAttribute("cartProducts", cartService.getCart(session));
        return "cart";
    }

    @PostMapping("/{productId}")
    public String addProductToCart(@PathVariable Long productId, @RequestParam int quantity, HttpSession session, Model model) {
        try {
            cartService.addToCart(productId, quantity, session);
        } catch (Exception e) {
            return "redirect:/cart?error=true";
        }
        return "redirect:/cart";
    }

    @GetMapping("/purchase")
    public String showPurchaseForm(HttpSession session, Model model) {
        // セッションからカートの商品を取得
        model.addAttribute("cartProducts", cartService.getCart(session));
        return "purchase_form";  // purchase_form.html に遷移
    }


    @GetMapping("/cart_detail")
    public String showCartDetail(HttpSession session, Model model) {
        model.addAttribute("cartProducts", cartService.getCart(session));
        return "cart/cart_detail";
    }

    @PostMapping("/{id}/add-to-cart")
    public String addProductToCart(@PathVariable Long id, @RequestParam(defaultValue = "1") int quantity, HttpSession session) {
        service.addToCart(id, quantity, session);
        return "redirect:/cart";
    }
}
