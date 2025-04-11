package jp.co.sss.test_spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.test_spring.entity.Order;
import jp.co.sss.test_spring.service.CartService;
import jp.co.sss.test_spring.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;  // カートサービスを追加

    // 購入情報入力フォーム表示
    @GetMapping("/purchase")
    public String showPurchaseForm(Model model) {
        model.addAttribute("cart", cartService.getCartItems());  // カート内の商品をモデルに追加
        return "purchase_form"; // purchase_form.html へ遷移
    }

    // 購入処理の実行
    @PostMapping("/purchase")
    public String submitPurchase(Model model) {
        List<Order> orders = orderService.getAllOrders();  
        orderService.saveAllOrders(orders);  // 一括で購入処理

        // 購入完了後、カートをクリア
        cartService.clearCart(null);

        return "purchase_confirmation";  // 確認画面へ
    }
}
