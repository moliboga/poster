package com.ss.poster.controller;

import com.ss.poster.dto.DtoMapping;
import com.ss.poster.dto.UserDto;
import com.ss.poster.model.User;
import com.ss.poster.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final DtoMapping dtoMapping;
    private final GetCurrentUserIdFromAuth getUser;

    public UserController(UserService userService, DtoMapping dtoMapping, GetCurrentUserIdFromAuth getUser) {
        this.userService = userService;
        this.dtoMapping = dtoMapping;
        this.getUser = getUser;
    }

//    todo: add accesses for admins and moders

//    @PostMapping("/create")
//    public UserDto create(@RequestBody UserDto instance) {
//        return dtoMapping.mapUserToDto(userService.create(dtoMapping.mapDtoToUser(instance)));
//    }

    @GetMapping("/all")
    public List<UserDto> getAll() {
        return userService.getAll().stream()
                .map(dtoMapping::mapUserToDtoWithoutPosts)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable("id") Long id){
        return dtoMapping.mapUserToDto(userService.getById(id));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UserDto delete(){
        Long id = getUser.getCurrentUserId();
        UserDto userDto = dtoMapping.mapUserToDto(userService.getById(id));
        userService.delete(id);
        return userDto;
    }

//    todo
    @PutMapping(value="/update")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UserDto update(@RequestBody User instance) {
        Long id = getUser.getCurrentUserId();
        return dtoMapping.mapUserToDto(userService.update(id, instance));
    }
}
