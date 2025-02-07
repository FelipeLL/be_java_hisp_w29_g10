package com.project.be_java_hisp_w29_g10.Integratioon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.be_java_hisp_w29_g10.dto.request.PostRequestDto;
import com.project.be_java_hisp_w29_g10.dto.request.ProductRequestDto;
import com.project.be_java_hisp_w29_g10.dto.response.PromoPostCountDto;
import org.junit.jupiter.api.BeforeEach;
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
    void getPromoPostNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/promo-post/count?user_id=100")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

}