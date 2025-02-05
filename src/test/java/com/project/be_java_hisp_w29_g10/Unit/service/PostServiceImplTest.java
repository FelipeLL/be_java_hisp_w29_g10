package com.project.be_java_hisp_w29_g10.Unit.service;

import com.project.be_java_hisp_w29_g10.dto.response.PostResponseDto;
import com.project.be_java_hisp_w29_g10.dto.response.ProductResponseDto;
import com.project.be_java_hisp_w29_g10.dto.response.RecentPostsResponseDto;
import com.project.be_java_hisp_w29_g10.exception.NotFoundException;
import com.project.be_java_hisp_w29_g10.repository.IPostRepository;
import com.project.be_java_hisp_w29_g10.repository.IProductRepository;
import com.project.be_java_hisp_w29_g10.repository.IUserRepository;
import com.project.be_java_hisp_w29_g10.service.IFollowService;
import com.project.be_java_hisp_w29_g10.service.ISellerService;
import com.project.be_java_hisp_w29_g10.service.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private IPostRepository postRepository;
    @Mock
    private ISellerService sellerService;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private IFollowService followService;
    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void save() {
    }

    @Test
    void savePromo() {
    }

    @Test
    void getPostsBySellerId() {
    }

    @Test
    void getPromoPostCountBySellerId() {
    }

    @Test
    void getRecentPostsByFollowedSellers() {
    }

    @Test
    @DisplayName("US5 - Happy Path")
    void orderByDateTypeOkTest() {
        //arrange
        RecentPostsResponseDto recentPost = generateRecentPost();
        String order = "date_asc";
        //act
        RecentPostsResponseDto recentPostsOrder = postService.OrderByDate(recentPost,order);
        //assert
        assertNotEquals(null,recentPostsOrder);
    }

    @Test
    @DisplayName("US5 - Throw Not Found Exception")
    void orderByDateTypeThrowNotFoundExceptionTest(){
        //arrange
        RecentPostsResponseDto recentPost = generateRecentPost();
        String order = "dateasc";
        //act and assert
        assertThrows(NotFoundException.class,() -> {postService.OrderByDate(recentPost,order);});
    }

    @Test
    @DisplayName("US6 - Happy Path")
    void orderByDateOkTest() {
        // Arrange
        RecentPostsResponseDto recentPosts = generateRecentPost();

        // Crear copias independientes de las listas de posts
        List<PostResponseDto> originalPosts = new ArrayList<>(recentPosts.getPosts());

        // Ascendente
        List<PostResponseDto> postsAsc = new ArrayList<>(originalPosts);
        postsAsc.sort(Comparator.comparing(PostResponseDto::getDate));

        // Descendente
        List<PostResponseDto> postsDesc = new ArrayList<>(originalPosts);
        postsDesc.sort(Comparator.comparing(PostResponseDto::getDate).reversed());

        // Act and assert
        RecentPostsResponseDto orderAsc = postService.OrderByDate(recentPosts, "date_asc");
        assertEquals(postsAsc, orderAsc.getPosts());

        RecentPostsResponseDto orderDesc = postService.OrderByDate(recentPosts, "date_desc");
        assertEquals(postsDesc, orderDesc.getPosts());
    }

    RecentPostsResponseDto generateRecentPost(){
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

        return new RecentPostsResponseDto(1001L, posts);
    }
}