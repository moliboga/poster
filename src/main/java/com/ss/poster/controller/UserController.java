package com.ss.poster.controller;

import com.ss.poster.dto.DtoMapping;
import com.ss.poster.dto.UserDto;
import com.ss.poster.model.User;
import com.ss.poster.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final DtoMapping dtoMapping;

    public UserController(UserService userService, DtoMapping dtoMapping) {
        this.userService = userService;
        this.dtoMapping = dtoMapping;
    }

    @GetMapping()
    public List<UserDto> getAll() {
        return userService.getAll().stream()
                .map(dtoMapping::mapUserToDtoWithoutPosts)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable("id") Long id){
        return dtoMapping.mapUserToDto(userService.getById(id));
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UserDto delete(){
        Long id = userService.getCurrentUserId();
        UserDto userDto = dtoMapping.mapUserToDto(userService.getById(id));
        userService.delete(id);
        return userDto;
    }

    @PutMapping()
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UserDto update(@RequestBody User instance) {
        return dtoMapping.mapUserToDto(userService.update(instance));
    }
}
