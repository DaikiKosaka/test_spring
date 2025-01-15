package jp.co.sss.test_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.sss.test_spring.repository.CategoryRepository;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // カテゴリー情報をモデルに追加
    @ModelAttribute
    public void addCategoriesToModel(Model model) {
        // データベースからすべてのカテゴリーを取得
        model.addAttribute("categories", categoryRepository.findAll());
    }
}
