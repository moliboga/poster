package com.ss.poster.dto;

import com.ss.poster.model.Post;
import com.ss.poster.model.User;
import com.ss.poster.service.PostService;
import com.ss.poster.service.UserService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DtoMapping {
    private final UserService userService;
    private final PostService postService;

    public DtoMapping(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    public Post mapDtoToPost(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setCreatedAt(postDto.getCreatedDate());
        post.setUpdatedAt(postDto.getUpdatedDate());
        post.setContent(postDto.getContent());
        post.setUser(userService.getById(postDto.getUserId()));
        post.setRepliedAt(postService.getById(postDto.getRepliedAt()));
        post.setReplies(postService
                .getAll().stream()
                .filter(p -> Objects.equals(p.getRepliedAt().getId(), postDto.getId()))
                .collect(Collectors.toList()));
        return post;
    }

    public PostDto mapPostToDtoWithoutReplies(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setCreatedDate(post.getCreatedAt());
        postDto.setUpdatedDate(post.getUpdatedAt());
        postDto.setContent(post.getContent());
        postDto.setUserId(post.getUser().getId());
        postDto.setRepliedAt(post.getRepliedAt().getId());
        postDto.setReplies(new ArrayList<>());
        return postDto;
    }

    public PostDto mapPostToDto(Post post){
        PostDto postDto = mapPostToDtoWithoutReplies(post);
        postDto.setReplies(post
                .getReplies().stream()
                .map(this::mapPostToDtoWithoutReplies)
                .collect(Collectors.toList()));
        return postDto;
    }

    public UserDto mapUserToDto(User user){
        UserDto userDto = mapUserToDtoWithoutPosts(user);
        userDto.setPosts(user.getPosts()
                .stream().map(this::mapPostToDtoWithoutReplies)
                .collect(Collectors.toList()));
        return userDto;
    }

    public UserDto mapUserToDtoWithoutPosts(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setCreatedDate(user.getCreatedAt());
        userDto.setUpdatedDate(user.getUpdatedAt());
        userDto.setNickname(user.getNickname());
        userDto.setName(user.getName());
        userDto.setInfo(user.getInfo());
        userDto.setPosts(new ArrayList<>());
        return userDto;
    }

    public User mapDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setCreatedAt(userDto.getCreatedDate());
        user.setUpdatedAt(userDto.getUpdatedDate());
        user.setNickname(userDto.getNickname());
        user.setName(userDto.getName());
        user.setInfo(userDto.getInfo());
        user.setPosts(postService.getAll()
                .stream()
                .filter(post -> Objects.equals(post.getUser().getId(), userDto.getId()))
                .collect(Collectors.toList()));
        return user;
    }
}
