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
        model.addAttribute("cartProducts", service.getCart(session));
        return "cart";
    }

    // 商品をカートに追加
    @PostMapping("/{productId}")
    public String addProductToCart(@PathVariable Long productId, @RequestParam int quantity, HttpSession session, Model model) {
        try {
            service.addToCart(productId, quantity, session);
        } catch (Exception e) {
            return "redirect:/cart?error=true";  // エラーが発生した場合、カートページにリダイレクト
        }
        if (session.getAttribute("user") == null) {
        	model.addAttribute("error", "ログインしてからカートに追加してください。");
        	return "redirect:/login";
        }
        return "redirect:/cart";
    }

    // 商品購入ページ
    @GetMapping("/purchase/{productId}")
    public String purchaseProduct(@PathVariable Long productId, Model model) {
        try {
            Product product = service.findProductById(productId);
            model.addAttribute("product", product);
        } catch (Exception e) {
            model.addAttribute("error", "商品情報の取得に失敗しました。");
            return "/product";
        }
        return "products/purchase";
    }
    
 // カート詳細ページを表示
    @GetMapping("/cart_detail")
    public String showCartDetail(HttpSession session, Model model) {
        model.addAttribute("cartProducts", service.getCart(session));
        return "cart/cart_detail"; // cart_detail.html をテンプレートとして使用
    }

}
