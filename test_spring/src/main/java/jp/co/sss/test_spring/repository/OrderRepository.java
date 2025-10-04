package jp.co.sss.test_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.test_spring.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
