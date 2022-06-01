package com.ss.poster.service;

import com.ss.poster.dto.PostDto;
import com.ss.poster.dto.UserDto;
import com.ss.poster.model.Post;
import com.ss.poster.model.User;
import com.ss.poster.repository.PostRepository;
import com.ss.poster.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post create(Post instance) {
        return postRepository.save(instance);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post getById(Long postId) {
        if (postRepository.findById(postId).isPresent())
            return postRepository.findById(postId).get();
        return null;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public Post like(Long postId, Long userId){
        User user = userRepository.findById(userId).get();
        Post post = getById(postId);
        if (post.getLikes().contains(user)){
            post.getLikes().remove(user);
        }
        else{
            post.getLikes().add(user);
        }
        return postRepository.save(post);
    }

    public List<User> getLikesByPostId(Long postId) {
        if (postRepository.findById(postId).isPresent())
            return postRepository.findById(postId).get().getLikes();
        return null;
    }
}
