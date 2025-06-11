package jp.co.sss.test_spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.entity.Cart;
import jp.co.sss.test_spring.entity.Order;
import jp.co.sss.test_spring.service.OrderService;

@Controller
@RequestMapping("/order") // ★ HTMLと一致させた
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 購入確認フォームを表示
    @GetMapping("/purchase")
    public String showPurchaseForm(HttpSession session, Model model) {
        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");

        int totalPrice = 0;
        if (cartList != null) {
            for (Cart cart : cartList) {
                totalPrice += cart.getProduct().getPrice() * cart.getQuantity();
            }
        }

        model.addAttribute("cartProducts", cartList);
        model.addAttribute("totalPrice", totalPrice);

        return "order/purchase_form";  // resources/templates/order/purchase_form.html
    }

    // 購入確定処理
    @PostMapping("/purchase")
    public String completePurchase(
            @RequestParam String address,
            @RequestParam String apartment,
            @RequestParam String payment,
            HttpSession session,
            Model model) {

        List<Cart> cartList = (List<Cart>) session.getAttribute("cart");

        if (cartList == null || cartList.isEmpty()) {
            model.addAttribute("error", "カートが空です。");
            return "order/purchase_form";
        }

        // 仮のユーザーID
        Long userId = 1L;

        Long orderId = orderService.registerOrder(userId, cartList);

        session.removeAttribute("cart");

        return "redirect:/test_spring/order/detail/" + orderId;
    }

    // 購入詳細画面
    @GetMapping("/detail/{orderId}")
    public String showOrderDetail(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderWithItems(orderId);
        model.addAttribute("order", order);
        return "order/order_detail";
    }
    
}
