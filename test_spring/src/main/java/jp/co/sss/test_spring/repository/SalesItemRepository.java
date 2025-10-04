package jp.co.sss.test_spring.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.test_spring.entity.SalesItem;

public interface SalesItemRepository extends JpaRepository<SalesItem, Integer> {
    List<SalesItem> findByEndMonthAfter(LocalDate today);
    List<SalesItem> findByProductId(Integer productId);

}
