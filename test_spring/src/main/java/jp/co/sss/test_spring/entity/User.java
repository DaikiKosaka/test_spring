package jp.co.sss.test_spring.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "{user.username.required}")
    @Size(max = 50, message = "{user.username.size}")
    @Column(name = "user_name")
    private String username;

    @Column(name = "user_name_kana")
    private String usernamekana;

    @NotBlank(message = "{user.email.required}")
    @Email(message = "{user.email.invalid}")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "{user.password.required}")
    @Size(min = 8, message = "{user.password.size}")
    @Column(name = "passwords")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "user_address")
    private String useraddress;

    @Transient
    @NotBlank(message = "{user.confirm.required}")
    private String confirmPassword;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // ゲッターとセッター
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getUsernamekana() { return usernamekana; }
    public void setUsernamekana(String usernamekana) { this.usernamekana = usernamekana; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getUserAddress() { return useraddress; }
    public void setUserAddress(String fullAddress) { this.useraddress = fullAddress; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
