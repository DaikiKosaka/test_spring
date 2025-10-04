package jp.co.sss.test_spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.entity.Review;
import jp.co.sss.test_spring.entity.User;
import jp.co.sss.test_spring.service.ReviewService;

@Controller
@RequestMapping("/mypage")
public class UserController {

    private final ReviewService reviewService;

    public UserController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public String showMyPage(HttpSession session, Model model) {
    	User user = (User) session.getAttribute("loginUser");
    	System.out.println("セッションユーザー: " + user); 
    	if (user == null) {
    	    return "redirect:/login";
    	}


        List<Review> reviews = reviewService.findByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
        return "user/mypage";
    }
}
