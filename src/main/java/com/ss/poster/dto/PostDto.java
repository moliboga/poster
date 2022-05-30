package com.ss.poster.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDto extends BaseDto{
    private String content;
    private Long userId;
    private Long repliedAt;
    private List<PostDto> replies;
}
