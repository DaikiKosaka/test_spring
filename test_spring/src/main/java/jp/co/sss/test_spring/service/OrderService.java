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

    // 例：@Lookup などの抽象メソッドがないか確認
    // もしあれば、実装を追加するか、@Lookupを正しく使う

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Long registerOrder(Long userId, List<Cart> cartList) {
        // 実装を書く
        return 1L; // 仮の戻り値
    }

    public Order getOrderWithItems(Long orderId) {
        // 実装を書く
        return orderRepository.findById(orderId).orElse(null);
    }
}

