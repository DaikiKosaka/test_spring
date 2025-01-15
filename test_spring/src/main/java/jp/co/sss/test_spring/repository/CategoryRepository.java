package jp.co.sss.test_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.test_spring.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 例えば、親カテゴリーごとにカテゴリーを取得するカスタムクエリを定義することもできます
    List<Category> findByParentCategory(Category parentCategory);
}
