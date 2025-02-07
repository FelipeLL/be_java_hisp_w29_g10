package com.project.be_java_hisp_w29_g10.Unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.be_java_hisp_w29_g10.dto.response.FollowedSellerDto;
import com.project.be_java_hisp_w29_g10.dto.response.FollowerDto;
import com.project.be_java_hisp_w29_g10.dto.response.SellerFollowersDto;
import com.project.be_java_hisp_w29_g10.dto.response.UserFollowedSellerDto;
import org.junit.jupiter.api.DisplayName;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;



    @Test
    void followSeller() {
    }

    @Test
    void unfollowSeller() {
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