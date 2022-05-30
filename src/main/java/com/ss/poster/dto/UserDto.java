package com.ss.poster.dto;

import com.ss.poster.model.Post;
import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class UserDto extends BaseDto{
    private String nickname;
    private String name;
    private String info;
    private List<PostDto> posts;
}
