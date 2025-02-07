package com.project.be_java_hisp_w29_g10.Integratioon.controller;

import com.project.be_java_hisp_w29_g10.dto.response.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    
    @Test
    @DisplayName("US2 - Test getCountFollowers - OK")
    void getCountFollowers() throws Exception {
        FollowersCountDto followersCountDto = new FollowersCountDto(1L,"Andrés", 5);
        mockMvc.perform(get("/users/{userId}/followers/count", 1l))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(followersCountDto.getUser_id()))
                .andExpect(jsonPath("$.user_name").value(followersCountDto.getUser_name()))
                .andExpect(jsonPath("$.follow_count").value(followersCountDto.getFollow_count()));
    }

    
    @DisplayName("US2 - Test getCountFollowers - Not Found")
    @Test
    void getCountFollowersThrowNotFoundException() throws Exception{
        mockMvc.perform(get("/users/{userId}/followers/count", 999L))
                .andExpect(status().isNotFound());
    }

    
    @DisplayName("US3 - Test getSellerAndFollowers - OK")
    @Test
    void getSellerAndFollowersWithoutOrder() throws Exception {
        SellerFollowersDto sellerFollowersDto = new SellerFollowersDto(1L,"Andrés",
                List.of(new FollowerDto(2L,"Bob"),
                        new FollowerDto(1L, "Alice"),
                        new FollowerDto(10L, "Jack"),
                        new FollowerDto(9L, "Ivy"),
                        new FollowerDto(3L, "Charlie"))
        );
        mockMvc.perform(get("/users/{userId}/followers/list", 1l))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(sellerFollowersDto.getUser_id()))
                .andExpect(jsonPath("$.user_name").value(sellerFollowersDto.getUser_name()))
                .andExpect(jsonPath("$.followers[0].user_id").value(sellerFollowersDto.getFollowers().get(0).getUser_id()))
                .andExpect(jsonPath("$.followers[0].user_name").value(sellerFollowersDto.getFollowers().get(0).getUser_name()))
                .andExpect(jsonPath("$.followers[1].user_id").value(sellerFollowersDto.getFollowers().get(1).getUser_id()))
                .andExpect(jsonPath("$.followers[1].user_name").value(sellerFollowersDto.getFollowers().get(1).getUser_name()))
                .andExpect(jsonPath("$.followers[2].user_id").value(sellerFollowersDto.getFollowers().get(2).getUser_id()))
                .andExpect(jsonPath("$.followers[2].user_name").value(sellerFollowersDto.getFollowers().get(2).getUser_name()))
                .andExpect(jsonPath("$.followers[3].user_id").value(sellerFollowersDto.getFollowers().get(3).getUser_id()))
                .andExpect(jsonPath("$.followers[3].user_name").value(sellerFollowersDto.getFollowers().get(3).getUser_name()))
                .andExpect(jsonPath("$.followers[4].user_id").value(sellerFollowersDto.getFollowers().get(4).getUser_id()))
                .andExpect(jsonPath("$.followers[4].user_name").value(sellerFollowersDto.getFollowers().get(4).getUser_name()));
    }

    
    @DisplayName("US3 - Test getSellerAndFollowers - Not Found")
    @Test
    void getSellerAndFollowersThrowNotFoundException() throws Exception {
        mockMvc.perform(get("/users/{userId}/followers/list", 999L))
                .andExpect(status().isNotFound());
    }

    
    @Test
    @DisplayName("US4 - Test getUserAndFollowedSellers - OK")
    void getUserAndFollowedSellersWithoutOrder() throws Exception{
        UserFollowedSellerDto userFollowedSellerDto = new UserFollowedSellerDto(10L,"Jack",
                List.of(new FollowedSellerDto(1L,"Andrés"),
                        new FollowedSellerDto(3L,"Carlos"),
                        new FollowedSellerDto(5L,"Elena"),
                        new FollowedSellerDto(8L,"Hernando"))
        );
        mockMvc.perform(get("/users/{userId}/followed/list",10L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(userFollowedSellerDto.getUser_id()))
                .andExpect(jsonPath("$.user_name").value(userFollowedSellerDto.getUser_name()))
                .andExpect(jsonPath("$.followed[0].user_id").value(userFollowedSellerDto.getFollowed().get(0).getUser_id()))
                .andExpect(jsonPath("$.followed[0].user_name").value(userFollowedSellerDto.getFollowed().get(0).getUser_name()))
                .andExpect(jsonPath("$.followed[1].user_id").value(userFollowedSellerDto.getFollowed().get(1).getUser_id()))
                .andExpect(jsonPath("$.followed[1].user_name").value(userFollowedSellerDto.getFollowed().get(1).getUser_name()));
    }


    @Test
    @DisplayName("US4 - Test getUserAndFollowedSellers - Not Found")
    void getUserAndFollowedSellersThrowNotFoundException() throws Exception{
        mockMvc.perform(get("/users/{userId}/followed/list", 999L))
                .andExpect(status().isNotFound());
    }


}
