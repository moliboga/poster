package com.ss.poster.dto;

import com.ss.poster.model.Post;
import com.ss.poster.model.User;
import com.ss.poster.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class DtoMapping {
    private final UserService userService;

    public DtoMapping(UserService userService) {
        this.userService = userService;
    }

    public Post mapDtoToPost(PostDto postDto){
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setUser(userService.getById(postDto.getUserId()));
        return post;
    }

    public PostDto mapPostToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setContent(post.getContent());
        postDto.setUserId(post.getUser().getId());
        return postDto;
    }

    public UserDto mapUserToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setNickname(user.getNickname());
        userDto.setName(user.getName());
        userDto.setInfo(userDto.getInfo());
        return userDto;
    }
}
