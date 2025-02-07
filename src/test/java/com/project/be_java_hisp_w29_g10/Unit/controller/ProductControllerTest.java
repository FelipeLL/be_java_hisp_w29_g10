package com.project.be_java_hisp_w29_g10.Unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.be_java_hisp_w29_g10.dto.response.FollowedSellerDto;
import com.project.be_java_hisp_w29_g10.dto.response.PostResponseDto;
import com.project.be_java_hisp_w29_g10.dto.response.RecentPostsResponseDto;
import com.project.be_java_hisp_w29_g10.dto.response.UserFollowedSellerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void savePost() {
    }

    @Test
    void deletePost() {
    }

    @Test
    void savePromoPost() {
    }

    @Test
    void getPromoPost() {
    }

    @Test
    void getRecentPostsByFollowedSellers(){

    }

    @Test
    void getRecentPostsByFollowedSellersOrderByDateAscTest() throws Exception{
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/products/followed/{userId}/list?order=date_asc",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        RecentPostsResponseDto recentPostsResponseDto = objectMapper.readValue(result, RecentPostsResponseDto.class);
        List<PostResponseDto> recentPosts = recentPostsResponseDto.getPosts();

        List<PostResponseDto> sortedPost = new ArrayList<>(recentPosts);

        sortedPost.sort(Comparator.comparing(PostResponseDto::getDate));

        assertEquals(sortedPost, recentPosts);
    }

    @Test
    void getRecentPostsByFollowedSellersOrderByDateDescTest() throws Exception{
        String result = this.mockMvc.perform(MockMvcRequestBuilders.get("/products/followed/{userId}/list?order=date_desc",1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        RecentPostsResponseDto recentPostsResponseDto = objectMapper.readValue(result, RecentPostsResponseDto.class);
        List<PostResponseDto> recentPosts = recentPostsResponseDto.getPosts();

        List<PostResponseDto> sortedPost = new ArrayList<>(recentPosts);

        sortedPost.sort(Comparator.comparing(PostResponseDto::getDate).reversed());

        assertEquals(sortedPost, recentPosts);
    }

    @Test
    void getPostsBySeller() {
    }
}