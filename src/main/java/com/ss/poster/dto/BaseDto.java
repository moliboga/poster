package com.ss.poster.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDto {
    private Long id;
    private Date createdDate;
    private Date updatedDate;
}
