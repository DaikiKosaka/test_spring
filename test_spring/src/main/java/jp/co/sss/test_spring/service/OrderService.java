package jp.co.sss.test_spring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Order;
import jp.co.sss.test_spring.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}