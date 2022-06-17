package com.ss.poster.controller;

import com.ss.poster.dto.DtoMapping;
import com.ss.poster.dto.PostDto;
import com.ss.poster.dto.UserDto;
import com.ss.poster.model.Post;
import com.ss.poster.service.PostService;
import com.ss.poster.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final DtoMapping dtoMapping;

    public PostController(PostService postService, UserService userService, DtoMapping dtoMapping) {
        this.postService = postService;
        this.userService = userService;
        this.dtoMapping = dtoMapping;
    }

    @PostMapping()
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public PostDto create(@RequestBody PostDto instance) {
        instance.setUserId(userService.getCurrentUserId());
        Post post = dtoMapping.mapDtoToPost(instance);
        return dtoMapping.mapPostToDto(postService.create(post));
    }

    @GetMapping()
    public List<PostDto> getAll() {
        return postService.getAll().stream()
                .map(dtoMapping::mapPostToDtoWithoutReplies)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PostDto getById(@PathVariable("id") Long id){
        return dtoMapping.mapPostToDto(postService.getById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public PostDto delete(@PathVariable("id") Long id){
        Post post = postService.getById(id);
        if (Objects.equals(userService.getCurrentUsername(), post.getUser().getUsername())){
            PostDto postDto = dtoMapping.mapPostToDto(post);
            postService.delete(id);
            return postDto;
        }
        return null;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public PostDto likePost(@PathVariable("id") Long postId){
        return dtoMapping.mapPostToDto(postService.like(postId));
    }

    @GetMapping("/{id}/likes")
    public List<UserDto> getLikes(@PathVariable("id") Long postId){
        return postService.getLikesByPostId(postId)
                .stream().map(dtoMapping::mapUserToDtoWithoutPosts)
                .collect(Collectors.toList());
    }
}
