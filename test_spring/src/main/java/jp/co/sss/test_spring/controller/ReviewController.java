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
                            @AuthenticationPrincipal User user) throws IOException {

        // 他のバリデーションや処理...

        // 画像がアップロードされた場合の処理
        if (!reviewImg.isEmpty()) {
            // 画像ファイル名を取得
            String fileName = reviewImg.getOriginalFilename();

            // 保存先のパスを指定（アプリケーション外のユーザーディレクトリ）
            String uploadDir = System.getProperty("user.home") + "/uploaded_images";
            Path uploadPath = Paths.get(uploadDir);

            // 保存先ディレクトリが存在しない場合は作成
            Files.createDirectories(uploadPath);

            // 画像を保存するファイルパスを作成
            Path filePath = uploadPath.resolve(fileName);
            
            // ファイルを保存
            reviewImg.transferTo(filePath.toFile());

            // 画像パスをReviewに設定（相対パスを利用）
            review.setReviewImgPath("/uploaded_images/" + fileName);
        }

        // 口コミを保存
        review.setProductId(productId);
        reviewService.saveReview(productId, review);

        redirectAttributes.addFlashAttribute("message", "口コミが投稿されました！");
        return "redirect:/products/" + productId;
    }

}
