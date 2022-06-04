package com.ss.poster.service;

import com.ss.poster.model.User;
import com.ss.poster.repository.PostRepository;
import com.ss.poster.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public User create(User instance) {
        return userRepository.save(instance);
    }

    public String getCurrentUsername(){
        return ((UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public Long getCurrentUserId(){
        String username = getCurrentUsername();
        return userRepository.findByUsername(username).get().getId();
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long userId) {
        if (userRepository.findById(userId).isPresent())
            return userRepository.findById(userId).get();
        return null;
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User update(User instanceDetails) {
        String username = getCurrentUsername();
        if (userRepository.findByUsername(username).isPresent()){
            User user = userRepository.findByUsername(username).get();
            user.setName(instanceDetails.getName());
            user.setUsername(instanceDetails.getUsername());
            user.setInfo(instanceDetails.getInfo());
            user.setPosts(postRepository
                    .findAll().stream()
                    .filter(post ->
                            Objects.equals(post.getUser().getId(), user.getId()))
                    .collect(Collectors.toList()));
            return userRepository.save(user);
        }
        return null;
    }
}
