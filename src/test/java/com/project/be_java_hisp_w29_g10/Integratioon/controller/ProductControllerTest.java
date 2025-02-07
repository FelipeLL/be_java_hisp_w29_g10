package com.project.be_java_hisp_w29_g10.Integratioon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.be_java_hisp_w29_g10.dto.request.PostRequestDto;
import com.project.be_java_hisp_w29_g10.dto.request.ProductRequestDto;
import com.project.be_java_hisp_w29_g10.dto.response.PromoPostCountDto;
import com.project.be_java_hisp_w29_g10.dto.response.RecentPostsResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void savePostOkTest() throws Exception {
        ProductRequestDto productDto = new ProductRequestDto(
                1001L,
                "Laptop",
                "Electronics",
                "BrandX",
                "Black",
                "Latest model with extra features"
        );
        PostRequestDto postRequestDto = new PostRequestDto(
                1L,
                LocalDate.of(2023, 10, 20),
                2,
                199.99,
                productDto,
                10.0,
                true
        );

        String body = mapper.writeValueAsString(postRequestDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/products/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(body))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Publicación creada exitosamente."));
    }

    @Test
    @DisplayName("IntegrationTest-011: Happy Path")
    void getPromoPostOkTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/promo-post/count?user_id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        PromoPostCountDto response = mapper.readValue(result.getResponse().getContentAsString(), PromoPostCountDto.class);
        //assertions
        assertEquals(1L, response.getUser_id());
        assertEquals("Andrés", response.getUser_name());
        assertEquals(2L, response.getPromo_products_count());
    }

    @Test
    @DisplayName("IntegrationTest-011: Non Existing Seller")
    void getPromoPostNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/promo-post/count?user_id=100")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("IntegrationTest-010: Happy Path")
    void savePromoPostOkTest() throws Exception {
        PostRequestDto postRequestDto = new PostRequestDto(
                1L,
                LocalDate.of(2023, 10, 20),
                2,
                199.99,
                new ProductRequestDto(
                        1002L,
                        "Laptop",
                        "Electronics",
                        "BrandX",
                        "Black",
                        "Latest model with extra features"
                ),
                10.0,
                true
        );
        String body = mapper.writeValueAsString(postRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/products/promo-post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(body))
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Publicación con promo creada exitosamente."));
    }

    @Test
    @DisplayName("IntegrationTest-010: Invalid Promo Post")
    void savePromoPostInvalidPromoTest() throws Exception {
        PostRequestDto postRequestDto = new PostRequestDto(
                1L,
                LocalDate.of(2023, 10, 20),
                2,
                199.99,
                new ProductRequestDto(
                        1001L,
                        "Laptop",
                        "Electronics",
                        "BrandX",
                        "Black",
                        "Latest model with extra features"
                ),
                0.0,
                false
        );
        String body = mapper.writeValueAsString(postRequestDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/products/promo-post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(body))
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("IntegrationTest-006: Get Recent Posts by Followed Sellers - Happy Path")
    void getRecentPostsByFollowedSellersOkTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/followed/1/list")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        RecentPostsResponseDto response = mapper.readValue(result.getResponse().getContentAsString(), RecentPostsResponseDto.class);

        // Assertions
        assertNotNull(response);
        assertNotNull(response.getPosts());
    }

    @Test
    @DisplayName("IntegrationTest-006: Get Recent Posts with Date Order - Happy Path")
    void getRecentPostsByFollowedSellersWithOrderTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/products/followed/1/list")
                        .param("order", "date_desc")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        RecentPostsResponseDto response = mapper.readValue(result.getResponse().getContentAsString(), RecentPostsResponseDto.class);

        // Assertions
        assertNotNull(response);
        assertNotNull(response.getPosts());
    }

    @Test
    @DisplayName("IntegrationTest-006: Get Recent Posts by Followed Sellers - User Not Found")
    void getRecentPostsByFollowedSellersUserNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/followed/9999/list")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("IntegrationTest-006: Get Recent Posts by Followed Sellers - Invalid Order Parameter")
    void getRecentPostsByFollowedSellersInvalidOrderTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/followed/1/list")
                        .param("order", "invalid_order")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isBadRequest());
    }
}