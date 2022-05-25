package com.ss.poster.controller;

import com.ss.poster.dto.PostDto;
import com.ss.poster.dto.DtoMapping;
import com.ss.poster.model.Post;
import com.ss.poster.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final DtoMapping dtoMapping;

    public PostController(PostService postService, DtoMapping dtoMapping) {
        this.postService = postService;
        this.dtoMapping = dtoMapping;
    }

    @PostMapping
    public PostDto create(@RequestBody PostDto instance) {
        return dtoMapping.mapPostToDto(postService.create(dtoMapping.mapDtoToPost(instance)));
    }

    @GetMapping
    public List<PostDto> getAll() {
        return postService.getAll().stream()
                .map(dtoMapping::mapPostToDto)
                .collect(Collectors.toList());
    }
}
