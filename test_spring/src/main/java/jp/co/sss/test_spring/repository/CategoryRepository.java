package jp.co.sss.test_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.test_spring.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
