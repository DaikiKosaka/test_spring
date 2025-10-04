package jp.co.sss.test_spring.advice;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.sss.test_spring.entity.Category;
import jp.co.sss.test_spring.repository.CategoryRepository;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private CategoryRepository categoryRepository;

    @ModelAttribute
    public void addUserNameToModel(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("user_name");
        if (userName != null) {
            model.addAttribute("user_name", userName);
        }
    }

    @ModelAttribute("categories")
    public List<Category> categories() {
        try {
            List<Category> list = categoryRepository.findAll();
            System.out.println("📂 カテゴリ一覧取得: " + list.size() + "件");
            return list;
        } catch (Exception e) {
            System.out.println("⚠️ カテゴリ取得エラー: " + e.getMessage());
            return List.of();
        }
    }
}
