package jp.co.sss.test_spring.advice;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addUserNameToModel(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("user_name");
        if (userName != null) {
            model.addAttribute("user_name", userName);
        }
    }
}
