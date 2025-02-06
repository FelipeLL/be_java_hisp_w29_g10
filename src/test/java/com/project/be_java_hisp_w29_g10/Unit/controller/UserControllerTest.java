package com.project.be_java_hisp_w29_g10.Unit.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void followSeller() throws Exception {
        mockMvc.perform(post("/users/{userId}/follow/{userIdToFollow}",1L,5L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("message").value("El usuario 1 ahora sigue al vendedor 5"));
    }

    @Test
    void unfollowSeller() throws Exception{
        mockMvc.perform(post("/users/{userId}/unfollow/{userIdToFollow}",1L,4L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("message").value("El usuario 1 a dejado de seguir al vendedor 4"));
    }

    @Test
    void getCountFollowers() {
    }

    @Test
    void getSellerAndFollowers() {
    }

    @Test
    void getUserAndFollowedSellers() {
    }
}