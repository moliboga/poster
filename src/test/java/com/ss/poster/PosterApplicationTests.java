package com.ss.poster;

import com.ss.poster.model.Post;
import com.ss.poster.model.User;
import com.ss.poster.repository.PostRepository;
import com.ss.poster.repository.UserRepository;
import com.ss.poster.service.PostService;
import com.ss.poster.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PosterApplicationTests {

    @Mock
    PostService mockedPostService;

    @Mock
    UserService mockedUserService;

    @Mock
    UserRepository mockedUserRepository;

    @Mock
    PostRepository mockedPostRepository;

    @Test
    public void testLike() {
        List<User> users = new ArrayList<>();

        User user = new User();
        user.setId(0L);
        user.setUsername("selposhny");
        user.setName("artem");
        user.setInfo("java developer");
        users.add(user);

        User user2 = new User();
        user2.setId(1L);
        user2.setName("kosbi");
        user2.setInfo("java dedveloper");
        user2.setUsername("whalehugger");

        Post post = new Post();
        post.setLikes(users);

        PostService postService = new PostService(
                mockedPostRepository,
                mockedUserRepository,
                mockedUserService
        );

        when(mockedPostRepository.findById(anyLong())).thenReturn(Optional.of(post));

        int oldLikes = postService.getLikesByPostId(0L).size();

        Assertions.assertEquals(1, oldLikes);

        when(mockedUserRepository.findByUsername(anyString())).thenReturn(Optional.of(user2));
        when(mockedUserRepository.findByUsername(null)).thenReturn(Optional.of(user2));
        when(mockedPostRepository.save(post)).thenReturn(post);

        postService.like(0L);

        Assertions.assertEquals(postService.getLikesByPostId(0L).size(), oldLikes + 1);

        postService.like(0L);

        Assertions.assertEquals(postService.getLikesByPostId(0L).size(), oldLikes);
    }
}
