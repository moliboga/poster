package com.ss.poster.controller;

import com.ss.poster.dto.DtoMapping;
import com.ss.poster.dto.UserDto;
import com.ss.poster.model.User;
import com.ss.poster.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final DtoMapping dtoMapping;

    public UserController(UserService userService, DtoMapping dtoMapping) {
        this.userService = userService;
        this.dtoMapping = dtoMapping;
    }

    @PostMapping
    public User create(@RequestBody User instance) {
        return userService.create(instance);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll().stream()
                .map(dtoMapping::mapUserToDto)
                .collect(Collectors.toList());
    }
}
