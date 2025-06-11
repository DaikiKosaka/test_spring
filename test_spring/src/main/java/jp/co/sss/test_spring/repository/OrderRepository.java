package jp.co.sss.test_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.test_spring.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}