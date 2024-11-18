package com.caboooom.userservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.caboooom.userservice.entity.User;
import com.caboooom.userservice.repository.UserRepository;

@Service
public class SignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByUserId(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    /**
     * 일반 회원가입 로직을 수행합니다.
     */
    public void signUp(String username, String name, String email, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, name, email, encodedPassword, null);
        userRepository.save(user);
    }
}
