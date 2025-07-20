package jp.co.sss.test_spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.co.sss.test_spring.entity.Cart;
import jp.co.sss.test_spring.entity.Order;
import jp.co.sss.test_spring.repository.OrderRepository;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Long registerOrder(Long userId, List<Cart> cartList) {
        Order order = new Order();
        order.setUserId(userId);

        int totalAmount = cartList.stream()
                .mapToInt(cart -> cart.getProduct().getPrice() * cart.getQuantity())
                .sum();
        order.setTotalAmount(totalAmount);
        order.setStatus("NEW");
        order.setCreatedAt(java.time.LocalDateTime.now());
        order.setUpdatedAt(java.time.LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        return savedOrder.getOrderId();
    }

    public Order getOrderWithItems(Long orderId) {
        return orderRepository.findByIdWithItems(orderId).orElse(null);
    }
}
