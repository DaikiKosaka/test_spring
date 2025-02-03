package jp.co.sss.test_spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.test_spring.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUserId(Integer userId);  // ユーザーIDでカートアイテムを取得

    Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);  // ユーザーIDと商品IDでカートアイテムを取得

    void deleteByUserIdAndProductId(Long userId, Long productId);  // ユーザーIDと商品IDでカートアイテムを削除
}
