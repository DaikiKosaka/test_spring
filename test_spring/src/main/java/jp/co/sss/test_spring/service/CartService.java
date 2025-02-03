package jp.co.sss.test_spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jp.co.sss.test_spring.entity.Cart;
import jp.co.sss.test_spring.entity.Product;

@Service  // サービスクラスとして注釈を追加
public class CartService {

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
	
    public double calculateCartTotal(List<Product> products, List<Integer> quantities) {
        double total = 0;
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            int quantity = quantities.get(i);
            total += product.getPrice() * quantity;  // 商品価格 × 数量
        }
        return total * 1.1; // 消費税を加算
    }

	public List<Cart> getCartItemsByUserId(Integer userId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	
}
