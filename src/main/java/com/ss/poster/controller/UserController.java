package com.ss.poster.controller;

import com.ss.poster.dto.DtoMapping;
import com.ss.poster.dto.PostDto;
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
    public UserDto create(@RequestBody UserDto instance) {
        return dtoMapping.mapUserToDto(userService.create(dtoMapping.mapDtoToUser(instance)));
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll().stream()
                .map(dtoMapping::mapUserToDtoWithoutPosts)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable("id") Long id){
        return dtoMapping.mapUserToDto(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable("id") Long id){
        UserDto userDto = dtoMapping.mapUserToDto(userService.getById(id));
        userService.delete(id);
        return userDto;
    }

    @PutMapping(value="/{id}")
    public UserDto update(@PathVariable(value = "id") Long id, @RequestBody User instance) {
        return dtoMapping.mapUserToDto(userService.update(id, instance));
    }
}
