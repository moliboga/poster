package com.ss.poster.controller;

import com.ss.poster.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class GetCurrentUserIdFromAuth {
    private final UserService userService;

    public GetCurrentUserIdFromAuth(UserService userService) {
        this.userService = userService;
    }

    public Long getCurrentUserId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        return userService
                .getAll()
                .stream()
                .filter(user -> Objects.equals(user.getUsername(), username))
                .findAny()
                .get()
                .getId();
    }
}
