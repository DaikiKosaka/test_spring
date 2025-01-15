package jp.co.sss.test_spring.controller;

import java.io.File;
import java.io.IOException;

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

    // 新規口コミ投稿フォーム
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
                            @AuthenticationPrincipal User user) throws IOException { // ログインユーザーを取得

        // 手動バリデーション（titleのバリデーションは削除）
        if (review.getComment() == null || review.getComment().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "内容は必須です。");
            return "redirect:/products/" + productId + "/reviews/new";
        }

        if (review.getRating() < 1 || review.getRating() > 5) {
            redirectAttributes.addFlashAttribute("error", "レーティングは1から5の間で指定してください。");
            return "redirect:/products/" + productId + "/reviews/new";
        }

        // user_id を設定（ログインユーザーのIDを使用）
        review.setUserId(user.getId());

        // 画像がアップロードされた場合の処理
        if (!reviewImg.isEmpty()) {
            String fileName = reviewImg.getOriginalFilename();
            String filePath = "C:/path/to/your/images/" + fileName; // 保存先パスを指定
            reviewImg.transferTo(new File(filePath));  // ファイルを保存
            review.setReviewImgPath(filePath);  // 画像パスを設定
        }

        // 口コミを保存
        review.setProductId(productId);
        reviewService.saveReview(productId, review);

        // 成功メッセージをリダイレクトに渡す
        redirectAttributes.addFlashAttribute("message", "口コミが投稿されました！");

        // 商品詳細ページにリダイレクト
        return "redirect:/products/" + productId;
    }

}
