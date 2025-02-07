package com.project.be_java_hisp_w29_g10.Unit.controller;
import com.project.be_java_hisp_w29_g10.repository.IPostRepository;
import com.project.be_java_hisp_w29_g10.repository.IUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IPostRepository postRepository;

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
    @DisplayName("Integration test 8 - US 6")
    void getRecentPostsByFollowedSellersOkTest() throws Exception {
        mockMvc.perform(get("/products/followed/{userId}/list", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.user_id").value(1))
                .andExpect(jsonPath("$.posts[0].post_id").exists())
                .andExpect(jsonPath("$.posts[0].date").exists())
                .andExpect(jsonPath("$.posts[0].price").exists());

    }


    @Test
    void getPostsBySeller() {
    }
}