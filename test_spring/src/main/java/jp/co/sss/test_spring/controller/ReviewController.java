package jp.co.sss.test_spring.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.entity.Review;
import jp.co.sss.test_spring.entity.User;
import jp.co.sss.test_spring.service.ReviewService;

@Controller
@RequestMapping("/products")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{productId}/reviews/new")
    public String newReview(@PathVariable Long productId, Model model) {
        model.addAttribute("productId", productId);
        model.addAttribute("review", new Review());
        return "reviews/new";
    }

    @PostMapping("/{productId}/reviews")
    public String addReview(@PathVariable Long productId,
                            @ModelAttribute Review review,
                            @RequestParam("reviewImg") MultipartFile reviewImg,
                            RedirectAttributes redirectAttributes,
                            HttpSession session) throws IOException {

        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "ログインしてください。");
            return "redirect:/login";
        }

        if (reviewImg != null && !reviewImg.isEmpty()) {
            String originalName = reviewImg.getOriginalFilename();
            String fileName = Paths.get(originalName)
                                   .getFileName()
                                   .toString()
                                   .replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

            // ✅ 保存先をユーザーのホームディレクトリに変更
            String uploadDir = System.getProperty("user.home") + "/uploaded_images";
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            reviewImg.transferTo(filePath.toFile());

            review.setReviewImgPath("/uploaded_images/" + fileName);
        }

        review.setUser(user);
        review.setProductId(productId);
        reviewService.saveReview(productId, review);

        redirectAttributes.addFlashAttribute("message", "口コミが投稿されました！");
        return "redirect:/products/" + productId;
    }
}
