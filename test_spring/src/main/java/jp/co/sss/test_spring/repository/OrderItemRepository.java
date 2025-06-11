package jp.co.sss.test_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.test_spring.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
