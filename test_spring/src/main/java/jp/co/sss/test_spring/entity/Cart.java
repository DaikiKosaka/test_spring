package jp.co.sss.test_spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId; // カートID

    @Column(nullable = false)
    private Integer userId; // ユーザーID

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 商品

    @Column(nullable = false)
    private Integer quantity; // 数量

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 作成日時

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 更新日時

    // 商品をカートに追加
    public void addItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // 商品削除のメソッドは、Cartの管理方式に合わせて別途実装する必要があります。

    // カートに登録された商品情報を取得
    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

	public Object getItems() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
