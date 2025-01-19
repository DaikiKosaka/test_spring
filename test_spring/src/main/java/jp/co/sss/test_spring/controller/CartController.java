package jp.co.sss.test_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.entity.Product;
import jp.co.sss.test_spring.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService service;

    public CartController(ProductService service) {
        this.service = service;
    }

    // カートの表示
    @GetMapping
    public String showCart(HttpSession session, Model model) {
        model.addAttribute("cartProducts", service.getCartProducts(session));
        return "cart";
    }

    // 商品をカートに追加
    @PostMapping("/{productId}")
    public String addProductToCart(@PathVariable Long productId, @RequestParam int quantity, HttpSession session) {
        service.addToCart(productId, quantity, session);
        return "redirect:/cart";
    }

    // 商品購入ページ
    @GetMapping("/purchase/{productId}")
    public String purchaseProduct(@PathVariable Long productId, Model model) {
        Product product = service.findProductById(productId);
        model.addAttribute("product", product);
        return "products/purchase";
    }
}
