package jp.co.sss.test_spring.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId; // 商品ID

    private String productName; // 商品名
    private int price; // 価格
    private Double taxPrice; // 税込価格
    private Integer stock; // 在庫
    private String comment; // コメント
    private String imgPath; // 画像パス
    private Integer companyId; // 会社ID
    private Integer categoryId; // カテゴリID
    private Boolean includeTax; // 税込みフラグ

    // ゲッターとセッター
    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Double getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(Double taxPrice) {
        this.taxPrice = taxPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getIncludeTax() {
        return includeTax;
    }

    public void setIncludeTax(Boolean includeTax) {
        this.includeTax = includeTax;
    }


	public Object getId() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
