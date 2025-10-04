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

    // ログイン画面を表示 (GET)
    @GetMapping("/login")
    public String getLogin() {
        return "login/login"; // login.html に遷移
    }

    // ログイン処理 (POST)
    @PostMapping("/login")
    public String postLogin(String email, String password, Model model, HttpSession session) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            // ユーザー名をセッションに保存
            session.setAttribute("user_name", user.get().getUsername());
            session.setAttribute("loginUser", user.get());

            // セッションに保存されたユーザー名の確認
            System.out.println("ログイン後にセッションに保存されたユーザー名: " + session.getAttribute("user_name"));

            return "redirect:/products"; // ログイン成功後のリダイレクト
        } else {
            model.addAttribute("loginError", "メールアドレスまたはパスワードが正しくありません。");
            return "/login/login"; // ログイン失敗時に戻る
        }
    }

    // ログアウト処理
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // セッションを無効化
        return "redirect:/login"; // ログアウト後のリダイレクト先
    }
}
