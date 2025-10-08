package jp.co.sss.test_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
import jp.co.sss.test_spring.entity.User;
import jp.co.sss.test_spring.repository.UserRepository;

@Controller
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegister(Model model) {
        System.out.println("[GET] /register にアクセス");
        model.addAttribute("user", new User());
        return "register/register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String postRegister(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            result.rejectValue("username", "user.username.required", "ユーザー名は必須です");
        }

        if (user.getConfirmPassword() == null || user.getConfirmPassword().trim().isEmpty()) {
            result.rejectValue("confirmPassword", "user.confirm.required", "確認用パスワードは必須です");
        }

        if (user.getPassword() != null && !user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "user.confirm.mismatch", "パスワードが一致しません");
        }

        if (result.hasErrors()) {
            System.out.println("→ バリデーションエラーあり（" + result.getErrorCount() + "件）");
            result.getAllErrors().forEach(e -> System.out.println("  - " + e.getDefaultMessage()));
            return "register/register";
        }

        boolean emailExists = userRepository.findByEmail(user.getEmail()).isPresent();
        System.out.println("メール重複チェック: " + (emailExists ? "既に存在" : "未登録"));

        if (emailExists) {
            model.addAttribute("errorMessage", "このメールアドレスはすでに登録されています。");
            return "register/register";
        }

        userRepository.save(user);
        System.out.println("→ ユーザー登録完了: " + user.getEmail());

        return "redirect:/login";
    }
}
