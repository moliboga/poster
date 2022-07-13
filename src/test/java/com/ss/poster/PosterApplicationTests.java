package com.ss.poster;

import com.ss.poster.model.Post;
import com.ss.poster.model.User;
import com.ss.poster.repository.PostRepository;
import com.ss.poster.repository.UserRepository;
import com.ss.poster.service.PostService;
import com.ss.poster.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class PosterApplicationTests {

    @Mock
    UserService mockedUserService;
    @Mock
    UserRepository mockedUserRepository;
    @Mock
    PostRepository mockedPostRepository;

    private User user;
    private User user2;
    private PostService postService;
    private int likes;

    private void setUserToLike(User user){
        when(mockedUserRepository.findByUsername(null)).thenReturn(Optional.of(user));
    }

    private int getCurrentLikes(){
        return postService.getLikesByPostId(0L).size();
    }

    @BeforeEach
    public void init(){
        List<User> users = new ArrayList<>();

        user = new User();
        user.setId(0L);
        user.setUsername("selposhny");
        user.setName("artem");
        user.setInfo("java developer");
        users.add(user);

        user2 = new User();
        user2.setId(1L);
        user2.setName("kosbi");
        user2.setInfo("java dedveloper");
        user2.setUsername("libtarded");

        Post post = new Post();
        post.setLikes(users);

        postService = new PostService(
                mockedPostRepository,
                mockedUserRepository,
                mockedUserService
        );

        when(mockedPostRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(mockedPostRepository.save(post)).thenReturn(post);

        likes = postService.getLikesByPostId(0L).size();
    }

    @Test
    public void when_wasNotLikedAndLike_expectedCountOfLikes_plusOne(){
        setUserToLike(user2);
        postService.like(0L);
        Assertions.assertEquals(likes + 1, getCurrentLikes());
    }

    @Test
    public void when_wasLikedAndLike_expectedCountOfLikes_minusOne(){
        setUserToLike(user);
        postService.like(0L);
        Assertions.assertEquals(likes - 1, getCurrentLikes());
    }

    @Test
    public void when_wasNotLikedAndDoubleLike_expectedCountOfLikes_same(){
        setUserToLike(user2);
        postService.like(0L);
        postService.like(0L);
        Assertions.assertEquals(likes, getCurrentLikes());
    }

    @Test
    public void when_wasLikedAndDoubleLike_expectedCountOfLikes_same(){
        setUserToLike(user);
        postService.like(0L);
        postService.like(0L);
        Assertions.assertEquals(likes, getCurrentLikes());
    }
}
