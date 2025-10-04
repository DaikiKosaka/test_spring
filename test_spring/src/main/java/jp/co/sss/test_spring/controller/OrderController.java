package jp.co.sss.test_spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.entity.Cart;
import jp.co.sss.test_spring.entity.Order;
import jp.co.sss.test_spring.entity.OrderItem;
import jp.co.sss.test_spring.entity.Product;
import jp.co.sss.test_spring.entity.User;
import jp.co.sss.test_spring.repository.OrderRepository;
import jp.co.sss.test_spring.repository.UserRepository;
import jp.co.sss.test_spring.service.CartService;
import jp.co.sss.test_spring.service.OrderService;
import jp.co.sss.test_spring.service.ProductService;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @PostMapping("/orders/finalize")
    public String finalizeOrder(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String apartment,
            @RequestParam String payment,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Integer quantity,
            HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }

        String fullAddress = address + " " + apartment;
        loginUser.setUsername(name);
        loginUser.setUserAddress(fullAddress);
        userRepository.save(loginUser);

        User updatedUser = userRepository.findById(loginUser.getId()).orElse(loginUser);

        List<Cart> cartList;

        if (productId != null && quantity != null) {
            Product product = productService.findProductById(productId);
            if (product == null) {
                System.out.println("⚠️ 単品購入：商品が見つかりませんでした。productId=" + productId);
                return "redirect:/error";
            }

            Cart singleCart = new Cart();
            singleCart.setProduct(product);
            singleCart.setQuantity(quantity);
            cartList = List.of(singleCart);
        } else {
            cartList = cartService.getCart(session);
        }

        Order savedOrder = orderService.saveOrder(cartList, updatedUser, "finished");

        for (OrderItem item : savedOrder.getOrderItems()) {
            if (item.getProduct() != null) {
                Long pid = item.getProduct().getProductId().longValue();
                int qty = item.getQuantity();
                productService.reduceStock(pid, qty);
            }
        }

        session.removeAttribute("cart");
        session.setAttribute("payment", payment);

        return "redirect:/order/orders/finished?orderId=" + savedOrder.getOrderId();
    }

    @GetMapping("/orders/finished")
    public String finished(@RequestParam("orderId") Long orderId, HttpSession session, Model model) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            System.out.println("⚠️ 注文が見つかりませんでした。orderId: " + orderId);
            model.addAttribute("errorMessage", "注文情報が見つかりませんでした。");
            return "orders/finished";
        }

        List<OrderItem> items = order.getOrderItems();
        List<String> imageUrls = new ArrayList<>();

        for (OrderItem item : items) {
            if (item == null || item.getProduct() == null || item.getProduct().getImgPath() == null) {
                imageUrls.add("");
                System.out.println("⚠️ 商品画像パスが null のため空文字を追加");
            } else {
                String rawPath = item.getProduct().getImgPath().trim();
                String cleanPath = rawPath.replaceFirst("^/images/", "");
                String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/images/")
                        .path(cleanPath)
                        .toUriString();
                imageUrls.add(imageUrl);
            }
        }

        System.out.println("📍 ユーザー住所: " + order.getUser().getUserAddress());

        model.addAttribute("order", order);
        model.addAttribute("user", order.getUser());
        model.addAttribute("items", items);
        model.addAttribute("imageUrls", imageUrls);
        model.addAttribute("payment", session.getAttribute("payment"));

        return "orders/finished";
    }
}
