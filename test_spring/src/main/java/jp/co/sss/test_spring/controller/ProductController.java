package jp.co.sss.test_spring.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.test_spring.entity.Product;
import jp.co.sss.test_spring.entity.Review;
import jp.co.sss.test_spring.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // 商品一覧表示
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = service.findAllProducts();
        model.addAttribute("products", products);
        return "products/index";
    }

    // 新規商品作成フォーム
    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/new";
    }

    // 商品保存処理
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        service.saveProduct(product);
        return "redirect:/products";
    }

    // 商品詳細表示（口コミ含む）
    @GetMapping("/{id}")
    public String showProductDetail(@PathVariable("id") Long productId, Model model) {
        Product product = service.findProductById(productId);
        model.addAttribute("product", product);

        List<Review> reviews = service.findReviewsByProductId(productId);
        model.addAttribute("hasReviews", !reviews.isEmpty());
        model.addAttribute("reviews", reviews);

        return "products/detail";
    }

    // 商品名で検索した結果を表示
    @GetMapping("/search")
    public String searchProducts(@RequestParam("keyword") String keyword, Model model) {
        List<Product> products = service.findProductsByKeyword(keyword);
        model.addAttribute("products", products);
        return "products/search";
    }

    // 単品購入ページ遷移
    @GetMapping("/detail/{id}/purchase")
    public String purchaseProduct(@PathVariable("id") Long productId, Model model) {
        Product product = service.findProductById(productId);
        model.addAttribute("product", product);
        return "products/purchase";
    }
}
