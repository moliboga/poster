package com.ss.poster.service;

import com.ss.poster.model.User;
import com.ss.poster.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User instance) {
        return userRepository.save(instance);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long userId) {
        return userRepository.findById(userId).get();
    }
}
