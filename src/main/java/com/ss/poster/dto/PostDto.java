package com.ss.poster.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDto extends BaseDto{
    private Long userId;
    private Long repliedAt;
    private String content;
    private int repliesCount;
    private int likesCount;
    private List<PostDto> replies;
}
