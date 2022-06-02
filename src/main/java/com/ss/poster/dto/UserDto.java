package com.ss.poster.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto extends BaseDto{
    private String username;
    private String name;
    private String info;
    private List<PostDto> posts;
}
