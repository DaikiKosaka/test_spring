package jp.co.sss.test_spring.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.test_spring.entity.Product;
import jp.co.sss.test_spring.entity.SalesItem;
import jp.co.sss.test_spring.repository.CategoryRepository;
import jp.co.sss.test_spring.repository.ProductRepository;
import jp.co.sss.test_spring.service.ProductService;
import jp.co.sss.test_spring.service.SalesItemService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SalesItemService salesItemService;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String listProducts(Model model) {
        List<SalesItem> salesItems = salesItemService.getActiveSalesItems();
        if (salesItems == null) {
            salesItems = new ArrayList<>();
        }

        for (SalesItem item : salesItems) {
            if (item.getProductId() != null && item.getDiscountRate() != null) {
                Product product = service.findProductById(item.getProductId());
                if (product != null && product.getPrice() != null) {
                    BigDecimal price = BigDecimal.valueOf(product.getPrice());
                    BigDecimal discountRate = item.getDiscountRate();
                    BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discountRate.divide(BigDecimal.valueOf(100)));
                    BigDecimal discountedPrice = price.multiply(discountMultiplier);
                    item.setDiscountedPrice(discountedPrice);
                    item.setSalesImgPath(product.getImgPath());
                }
            }
        }

        model.addAttribute("salesItems", salesItems);
        model.addAttribute("categories", categoryRepository.findAll());
        return "products/index";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        return "products/new";
    }

    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product) {
        service.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String showProductDetail(@PathVariable Long id, Model model) {
        Product product = service.findProductById(id);
        if (product == null) {
            model.addAttribute("errorMessage", "商品が見つかりませんでした。");
            return "products/error/404";
        }

        model.addAttribute("product", product);

        List<SalesItem> saleItems = salesItemService.findByProductId(product.getProductId());
        if (saleItems == null) {
            saleItems = new ArrayList<>();
        }

        if (!saleItems.isEmpty()) {
            SalesItem firstItem = saleItems.get(0);
            if (product.getPrice() != null && firstItem.getDiscountRate() != null) {
                BigDecimal price = BigDecimal.valueOf(product.getPrice());
                BigDecimal discountRate = firstItem.getDiscountRate();
                BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discountRate.divide(BigDecimal.valueOf(100)));
                BigDecimal discountedPrice = price.multiply(discountMultiplier);
                product.setPrice(discountedPrice.doubleValue());
            }
        }

        model.addAttribute("saleItems", saleItems);
        model.addAttribute("reviews", service.findReviewsByProductId(id));
        model.addAttribute("categories", categoryRepository.findAll());
        return "products/detail";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) Integer categoryId,
                                 Model model) {

        List<Product> products;

        if (keyword != null && !keyword.isEmpty() && categoryId != null) {
            products = productRepository.findByProductNameContainingAndCategoryId(keyword, categoryId);
        } else if (keyword != null && !keyword.isEmpty()) {
            products = productRepository.findByProductNameContaining(keyword);
        } else if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        } else {
            products = productRepository.findAll();
        }

        List<SalesItem> salesItems = salesItemService.getActiveSalesItems();
        if (salesItems == null) {
            salesItems = new ArrayList<>();
        }

        for (SalesItem item : salesItems) {
            if (item.getProductId() != null && item.getDiscountRate() != null) {
                Product product = service.findProductById(item.getProductId());
                if (product != null && product.getPrice() != null) {
                    BigDecimal price = BigDecimal.valueOf(product.getPrice());
                    BigDecimal discountRate = item.getDiscountRate();
                    BigDecimal discountMultiplier = BigDecimal.ONE.subtract(discountRate.divide(BigDecimal.valueOf(100)));
                    BigDecimal discountedPrice = price.multiply(discountMultiplier);
                    item.setDiscountedPrice(discountedPrice);
                }
            }
        }

        model.addAttribute("products", products);
        model.addAttribute("salesItems", salesItems);
        model.addAttribute("categories", categoryRepository.findAll());
        return "products/search";
    }

    @GetMapping("/detail/{id}/purchase")
    public String purchaseProduct(@PathVariable Long id, Model model) {
        Product product = service.findProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        return "products/purchase";
    }
}
