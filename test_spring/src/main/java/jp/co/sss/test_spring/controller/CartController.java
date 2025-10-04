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
import jp.co.sss.test_spring.service.OrderService;
import jp.co.sss.test_spring.service.ProductService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // カート画面を表示
    @GetMapping
    public String showCart(HttpSession session, Model model) {
        List<Cart> cartList = cartService.getCart(session);

        int totalPrice = 0;
        if (cartList != null) {
            for (Cart cart : cartList) {
                Product product = cart.getProduct();
                Double price = product.getPrice();
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

    // 購入フォームを表示（旧）
    @GetMapping("/purchase")
    public String showPurchaseForm(HttpSession session, Model model) {
        model.addAttribute("cartProducts", cartService.getCart(session));
        return "purchase_form";
    }

    // カート詳細ページを表示
    @GetMapping("/cart_detail")
    public String showCartDetail(HttpSession session, Model model) {
        List<Cart> cartList = cartService.getCart(session);

        int totalPrice = 0;
        if (cartList != null) {
            for (Cart cart : cartList) {
                Product product = cart.getProduct();
                Double price = product.getPrice();
                int quantity = cart.getQuantity();
                totalPrice += price * quantity;
            }
        }

        model.addAttribute("cartProducts", cartList);
        model.addAttribute("totalPrice", totalPrice);

        return "cart/cart_detail";
    }

    // カートから商品削除
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long cartId, HttpSession session) {
        cartService.removeFromCartByCartId(cartId, session);
        return ResponseEntity.ok("削除成功");
    }

    // ✅ 単品購入確認画面（productIdとquantityを受け取る）
    @GetMapping("/confirm")
    public String confirmCart(@RequestParam(required = false) Long productId,
                              @RequestParam(required = false) Integer quantity,
                              HttpSession session,
                              Model model) {

        if (productId != null && quantity != null) {
            Product product = productService.findProductById(productId);
            if (product == null || product.getStock() < quantity) {
                model.addAttribute("errorMessage", "在庫が不足しています。");
                return "cart/error";
            }

            model.addAttribute("product", product);
            model.addAttribute("quantity", quantity);
            model.addAttribute("total", product.getPrice() * quantity);
        } else {
            List<Cart> cartList = cartService.getCart(session);
            model.addAttribute("cartList", cartList);
            double total = cartService.calculateCartTotal(cartList);
            model.addAttribute("total", total);
        }

        return "confirm";
    }

    // ✅ カート購入時の購入確定画面（POST）
    @PostMapping("/complete")
    public String completePurchase(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String apartment,
            @RequestParam String payment,
            HttpSession session,
            Model model) {

        session.setAttribute("name", name);
        session.setAttribute("address", address);
        session.setAttribute("apartment", apartment);
        session.setAttribute("payment", payment);

        model.addAttribute("name", name);
        model.addAttribute("address", address);
        model.addAttribute("apartment", apartment);
        model.addAttribute("payment", payment);

        List<Cart> cartList = cartService.getCart(session);
        double totalPrice = cartService.calculateCartTotal(cartList);

        model.addAttribute("cartProducts", cartList);
        model.addAttribute("totalPrice", totalPrice);

        return "cart/complete";
    }

    // ✅ カート購入時の購入確定画面（GET）
    @GetMapping("/complete")
    public String showCompletePage(HttpSession session, Model model) {
        model.addAttribute("name", session.getAttribute("name"));
        model.addAttribute("address", session.getAttribute("address"));
        model.addAttribute("apartment", session.getAttribute("apartment"));
        model.addAttribute("payment", session.getAttribute("payment"));

        List<Cart> cartList = cartService.getCart(session);
        double totalPrice = cartService.calculateCartTotal(cartList);

        model.addAttribute("cartProducts", cartList);
        model.addAttribute("totalPrice", totalPrice);

        return "cart/complete";
    }

    // ✅ 単品購入時の購入確定画面（POST）
    @PostMapping("/complete/single")
    public String completeSinglePurchase(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String apartment,
            @RequestParam String payment,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Integer quantity,
            HttpSession session,
            Model model) {

        session.setAttribute("name", name);
        session.setAttribute("address", address);
        session.setAttribute("apartment", apartment);
        session.setAttribute("payment", payment);
        session.setAttribute("productId", productId);
        session.setAttribute("quantity", quantity);

        model.addAttribute("name", name);
        model.addAttribute("address", address);
        model.addAttribute("apartment", apartment);
        model.addAttribute("payment", payment);
        model.addAttribute("productId", productId);
        model.addAttribute("quantity", quantity);

        if (productId != null && quantity != null) {
            Product product = productService.findProductById(productId);
            if (product != null) {
                model.addAttribute("product", product);
                model.addAttribute("totalPrice", product.getPrice() * quantity);
            }
        }

        return "cart/complete";
    }

}
