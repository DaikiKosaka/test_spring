package jp.co.sss.test_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.criteria.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

