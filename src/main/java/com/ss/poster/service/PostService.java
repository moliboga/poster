package com.ss.poster.service;

import com.ss.poster.model.Post;
import com.ss.poster.model.User;
import com.ss.poster.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
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
}
