package com.ss.poster.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UserDto {
    private String nickname;
    private String name;
    private String info;
}
