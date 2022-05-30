package com.ss.poster.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PostDto extends BaseDto{
    private String content;
    private Long userId;
}
