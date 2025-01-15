package jp.co.sss.test_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.sss.test_spring.entity.User;
import jp.co.sss.test_spring.form.RegisterForm;
import jp.co.sss.test_spring.repository.UserRepository;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    // 新規登録画面を表示 (GET)
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegister(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register/register"; // register.html に遷移
    }

    // 新規登録処理 (POST)
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String postRegister(RegisterForm form, Model model) {
        // メールアドレス重複チェック
        if (userRepository.findByEmail(form.getEmail()).isPresent()) {
            model.addAttribute("errorMessage", "このメールアドレスはすでに登録されています。");
            return "register/register";
        }

        // パスワード確認
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("errorMessage", "パスワードが一致しません。");
            return "register/register";
        }

        // ユーザー情報を保存
        User user = new User();
        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword()); // 実際にはパスワードをハッシュ化してください
        userRepository.save(user);

        model.addAttribute("successMessage", "登録が完了しました！");
        return "redirect:/login"; // ログイン画面にリダイレクト
    }
}
