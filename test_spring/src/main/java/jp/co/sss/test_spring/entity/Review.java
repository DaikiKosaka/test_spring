package jp.co.sss.test_spring.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    private Long userId;
    private Long productId;
    private Integer rating;
    private String comment;
    private String post;
    private String dummyUserName;
    private String reviewImgPath;

    private LocalDateTime createdDate;  // フィールド名変更
    private LocalDateTime updatedAt;

    // Getters and Setters
    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDummyUserName() {
        return dummyUserName;
    }

    public void setDummyUserName(String dummyUserName) {
        this.dummyUserName = dummyUserName;
    }

    public String getReviewImgPath() {
        return reviewImgPath;
    }

    public void setReviewImgPath(String reviewImgPath) {
        this.reviewImgPath = reviewImgPath;
    }

    public LocalDateTime getCreatedDate() {  // メソッド名変更
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // @PrePersist と @PreUpdate メソッドを追加して、作成時・更新時に日時を設定する
    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;  // createdAt -> createdDate
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

