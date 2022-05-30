package com.ss.poster.dto;

import lombok.Data;

@Data
public class UserWithOnlyId extends UserDto{
    private Long userId;
}
