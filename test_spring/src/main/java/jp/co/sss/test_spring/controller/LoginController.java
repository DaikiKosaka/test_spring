package jp.co.sss.test_spring.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.entity.User;
import jp.co.sss.test_spring.repository.UserRepository;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String getLogin() {
        return "login/login"; // login.html に遷移
    }

    @PostMapping("/login")
    public String postLogin(String email, String password, Model model, HttpSession session) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {

            session.setAttribute("user_name", user.get().getUsername());
            session.setAttribute("loginUser", user.get());

            return "redirect:/products";
        } else {
            model.addAttribute("loginError", "メールアドレスまたはパスワードが正しくありません。");
            return "/login/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
