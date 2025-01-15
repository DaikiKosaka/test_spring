package jp.co.sss.test_spring.service;

import org.springframework.stereotype.Service;

import jp.co.sss.test_spring.entity.User;
import jp.co.sss.test_spring.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String username, String password) {
        // ユーザー名とパスワードを使って認証するロジック
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
