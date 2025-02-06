package com.project.be_java_hisp_w29_g10.Unit.service;

import com.project.be_java_hisp_w29_g10.dto.response.PostResponseDto;
import com.project.be_java_hisp_w29_g10.dto.response.ProductResponseDto;
import com.project.be_java_hisp_w29_g10.dto.response.RecentPostsResponseDto;
import com.project.be_java_hisp_w29_g10.entity.Post;
import com.project.be_java_hisp_w29_g10.entity.Product;
import com.project.be_java_hisp_w29_g10.entity.User;
import com.project.be_java_hisp_w29_g10.exception.NotFoundException;
import com.project.be_java_hisp_w29_g10.repository.IPostRepository;
import com.project.be_java_hisp_w29_g10.repository.IUserRepository;
import com.project.be_java_hisp_w29_g10.repository.IProductRepository;
import com.project.be_java_hisp_w29_g10.service.IFollowService;
import com.project.be_java_hisp_w29_g10.service.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private IPostRepository postRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IFollowService followService;

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private User testUser;
    private Post recentPost;
    private Post oldPost;
    private Product mockProduct;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "test_user");

        recentPost = Post.builder()
                .post_id(100L)
                .user_id(2L)
                .date(LocalDate.now().minusDays(5))
                .product_id(200L)
                .category(1)
                .price(150.0)
                .build();

        oldPost = Post.builder()
                .post_id(101L)
                .user_id(2L)
                .date(LocalDate.now().minusDays(20))
                .product_id(201L)
                .category(2)
                .price(250.0)
                .build();

        mockProduct = Product.builder()
                .product_id(200L)
                .product_name("Mock Product")
                .type("Electronics")
                .brand("MockBrand")
                .color("Black")
                .notes("Test Notes")
                .build();
    }

    @Test
    @DisplayName("US6 - Happy Path")
    void getRecentPostsByFollowedSellersOkTest() {
        // Arrange
        Long userId = 1L;
        List<Long> followedSellers = Collections.singletonList(2L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(followService.getSellersFollowedByUser(userId)).thenReturn(followedSellers);
        when(postRepository.getPostsBySellerID(2L)).thenReturn(Arrays.asList(recentPost, oldPost));
        when(productRepository.getById(200L)).thenReturn(mockProduct);

        // Act
        RecentPostsResponseDto response = postService.getRecentPostsByFollowedSellers(userId);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getPosts().size());

        PostResponseDto returnedPost = response.getPosts().get(0);
        assertEquals(recentPost.getPost_id(), returnedPost.getPost_id());
        assertTrue(LocalDate.parse(returnedPost.getDate(), java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                .isAfter(LocalDate.now().minusWeeks(2)));
    }

    @Test
    @DisplayName("US6 - Throw Not Found Exception")
    void getRecentPostsByFollowedSellersThrowNotFoundExceptionTest() {
        // Arrange
        Long userId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> postService.getRecentPostsByFollowedSellers(userId));
    }

    @Test
    @DisplayName("US6 - (No Recent Posts)")
    void getRecentPostsByFollowedSellersOkNoRecentPostsTest() {
        // Arrange
        Long userId = 1L;
        List<Long> followedSellers = Collections.singletonList(2L);

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(followService.getSellersFollowedByUser(userId)).thenReturn(followedSellers);
        when(postRepository.getPostsBySellerID(2L)).thenReturn(Collections.singletonList(oldPost));

        // Act
        RecentPostsResponseDto response = postService.getRecentPostsByFollowedSellers(userId);

        // Assert
        assertNotNull(response);
        assertTrue(response.getPosts().isEmpty());
    }
}
