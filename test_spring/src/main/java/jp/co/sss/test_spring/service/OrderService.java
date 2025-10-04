package jp.co.sss.test_spring.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.test_spring.entity.Cart;
import jp.co.sss.test_spring.entity.Order;
import jp.co.sss.test_spring.entity.OrderItem;
import jp.co.sss.test_spring.entity.Product;
import jp.co.sss.test_spring.entity.User;
import jp.co.sss.test_spring.repository.OrderItemRepository;
import jp.co.sss.test_spring.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order saveOrder(List<Cart> cartList, User user, String status) {
        int totalAmount = 0;

        for (Cart cart : cartList) {
            Product product = cart.getProduct();
            if (product == null) continue;
            totalAmount += product.getPrice() * cart.getQuantity();
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus(status);
        order.setTotalAmount(totalAmount);

        orderRepository.save(order);

        // ✅ OrderItem をリストに追加して Order にセットする
        List<OrderItem> orderItemList = new ArrayList<>();

        for (Cart cart : cartList) {
            Product product = cart.getProduct();
            if (product == null) continue;

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(cart.getQuantity());
            item.setPrice(product.getPrice());
            item.setOrder(order);
            item.setCreatedAt(LocalDateTime.now());
            item.setUpdatedAt(LocalDateTime.now());

            orderItemRepository.save(item);
            orderItemList.add(item); // ✅ リストに追加
        }

        order.setOrderItems(orderItemList); // ✅ Order にセット

        return order;
    }
}
