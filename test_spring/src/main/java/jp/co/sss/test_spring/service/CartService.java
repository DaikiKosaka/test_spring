package jp.co.sss.test_spring.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.entity.Cart;
import jp.co.sss.test_spring.entity.Order;
import jp.co.sss.test_spring.entity.OrderItem;
import jp.co.sss.test_spring.entity.Product;
import jp.co.sss.test_spring.repository.OrderRepository;
import jp.co.sss.test_spring.repository.OrderItemRepository;

@Service  // サービスクラスとして注釈を追加
public class CartService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public CartService(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Product getProductById(Long id, HttpSession session) {
        // ここで商品情報を取得するロジックを実装
        // 例えば、データベースから商品情報を取得する
        return null;  // 仮の戻り値
    }

    public Cart getCart(HttpSession session) {
        return (Cart) session.getAttribute("cart");  // セッションからCartオブジェクトを取得
    }

    public static void addToCart(Long productId, int quantity, HttpSession session) {
        Product product = findProductById(productId); // 商品情報を取得
        Cart cart = (Cart) session.getAttribute("cart"); // セッションからカートを取得

        if (cart == null) {
            cart = new Cart(); // カートがない場合、新規作成
        }

        cart.addItem(product, quantity); // 商品をカートに追加
        session.setAttribute("cart", cart); // セッションに保存
    }

    private static Product findProductById(Long productId) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    // カートの商品をOrderとして注文を作成
    public Order createOrderFromCart(Long userId, HttpSession session) {
        Cart cart = getCart(session); // セッションからカートを取得

        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("カートにアイテムがありません。");
        }

        // 注文作成
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus("PENDING");  // 注文のステータス（仮に"保留"として設定）
        order.setCreatedAt(LocalDateTime.now());
        order.setCreatedAt(LocalDateTime.now());

        // 注文の保存
        orderRepository.save(order);

        // カート内の商品をOrderItemとしてOrderに追加
        for (Cart cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            orderItemRepository.save(orderItem);
        }

        // 注文後にカートをクリアする場合
        clearCart(session);

        return order;
    }

    public double calculateCartTotal(List<Product> products, List<Integer> quantities) {
        double total = 0;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantity = quantities.get(i);
            total += product.getPrice() * quantity;  // 商品価格 × 数量
        }
        return total * 1.1; // 消費税を加算
    }

    // ユーザーIDでカートの商品を取得するメソッド（仮実装）
    public List<Cart> getCartItemsByUserId(Integer userId) {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

    // カートのクリア
    public void clearCart(HttpSession session) {
        session.setAttribute("cart", new Cart());  // セッションから
