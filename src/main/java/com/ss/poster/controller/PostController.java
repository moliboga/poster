package com.ss.poster.controller;

import com.ss.poster.dto.PostDto;
import com.ss.poster.dto.DtoMapping;
import com.ss.poster.dto.UserDto;
import com.ss.poster.dto.UserWithOnlyId;
import com.ss.poster.model.Post;
import com.ss.poster.model.User;
import com.ss.poster.service.PostService;
import com.ss.poster.service.UserService;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final DtoMapping dtoMapping;

    public PostController(PostService postService, DtoMapping dtoMapping) {
        this.postService = postService;
        this.dtoMapping = dtoMapping;
    }

    @PostMapping
    public PostDto create(@RequestBody PostDto instance) {
        Post post = dtoMapping.mapDtoToPost(instance);
        return dtoMapping.mapPostToDto(postService.create(post));
    }

    @GetMapping
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
    public PostDto delete(@PathVariable("id") Long id){
        PostDto postDto = dtoMapping.mapPostToDto(postService.getById(id));
        postService.delete(id);
        return postDto;
    }

    @PutMapping("/{id}/like")
    public PostDto likePost(@RequestBody UserWithOnlyId user, @PathVariable("id") Long postId){
        return dtoMapping.mapPostToDto(postService.like(postId, user.getUserId()));
    }

    @GetMapping("/{id}/likes")
    public List<UserDto> getLikes(@PathVariable("id") Long postId){
        return postService.getLikesByPostId(postId)
                .stream().map(dtoMapping::mapUserToDtoWithoutPosts)
                .collect(Collectors.toList());
    }
}
