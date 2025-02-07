package com.project.be_java_hisp_w29_g10.Unit.service;

import com.project.be_java_hisp_w29_g10.dto.response.PostResponseDto;
import com.project.be_java_hisp_w29_g10.dto.response.ProductResponseDto;
import com.project.be_java_hisp_w29_g10.dto.response.RecentPostsResponseDto;
import com.project.be_java_hisp_w29_g10.entity.Post;
import com.project.be_java_hisp_w29_g10.entity.Product;
import com.project.be_java_hisp_w29_g10.entity.User;
import com.project.be_java_hisp_w29_g10.enums.DateOrderType;
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
import java.util.*;

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
    private RecentPostsResponseDto recentPostsRespons;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "test_user");

        List<PostResponseDto> posts = new ArrayList<>();
        ProductResponseDto product1 = new ProductResponseDto(1L, "Producto 1", "Tipo A", "Marca A", "Rojo", "Notas del producto 1");
        PostResponseDto post1 = new PostResponseDto(101L, 201L, "2023-10-01", product1, 1, 19.99);
        posts.add(post1);
        ProductResponseDto product2 = new ProductResponseDto(2L, "Producto 2", "Tipo B", "Marca B", "Azul", "Notas del producto 2");
        PostResponseDto post2 = new PostResponseDto(102L, 202L, "2023-10-02", product2, 2, 29.99);
        posts.add(post2);
        ProductResponseDto product3 = new ProductResponseDto(3L, "Producto 3", "Tipo C", "Marca C", "Verde", "Notas del producto 3");
        PostResponseDto post3 = new PostResponseDto(103L, 203L, "2023-10-03", product3, 3, 39.99);
        posts.add(post3);

        recentPostsRespons = new RecentPostsResponseDto(1001L, posts);

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
        PostResponseDto returnedPost = response.getPosts().getFirst();


        // Assert
        assertNotNull(response);
        assertEquals(1, response.getPosts().size());

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

    @Test
    @DisplayName("US9 - Happy Path Asc")
    void orderByDateAscOkTest() {
        // Arrange
        List<PostResponseDto> originalPosts = new ArrayList<>(recentPostsRespons.getPosts());

        List<PostResponseDto> postsAsc = new ArrayList<>(originalPosts);
        postsAsc.sort(Comparator.comparing(PostResponseDto::getDate));

        // Act and assert
        RecentPostsResponseDto orderAsc = postService.OrderByDate(recentPostsRespons, DateOrderType.DATE_ASC);
        assertEquals(postsAsc, orderAsc.getPosts());
    }

    @Test
    @DisplayName("US9 - Happy Path Desc")
    void orderByDateDescOkTest() {
        // Arrange
        List<PostResponseDto> originalPosts = new ArrayList<>(recentPostsRespons.getPosts());

        List<PostResponseDto> postsDesc = new ArrayList<>(originalPosts);
        postsDesc.sort(Comparator.comparing(PostResponseDto::getDate).reversed());

        //Act and assert
        RecentPostsResponseDto orderDesc = postService.OrderByDate(recentPostsRespons, DateOrderType.DATE_DESC);
        assertEquals(postsDesc, orderDesc.getPosts());
    }
}
