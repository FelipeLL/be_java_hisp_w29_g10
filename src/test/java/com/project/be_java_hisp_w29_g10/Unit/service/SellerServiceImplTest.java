package com.project.be_java_hisp_w29_g10.Unit.service;

import com.project.be_java_hisp_w29_g10.dto.response.FollowerDto;
import com.project.be_java_hisp_w29_g10.dto.response.SellerFollowersDto;
import com.project.be_java_hisp_w29_g10.enums.NameSort;
import com.project.be_java_hisp_w29_g10.service.SellerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class SellerServiceImplTest {
    SellerFollowersDto sellerFollowers;

    @InjectMocks
    SellerServiceImpl sellerService;

    @BeforeEach
    void setUp() {
        FollowerDto follower1 = new FollowerDto(101L, "Alice");
        FollowerDto follower2 = new FollowerDto(102L, "Bob");
        FollowerDto follower3 = new FollowerDto(103L, "Charlie");

        sellerFollowers = new SellerFollowersDto(100L, "Juan Pablo", Arrays.asList(follower2, follower3, follower1));
    }

    @Test
    void getCountFollowers() {
    }

    @Test
    void getSellerAndFollowers() {
    }

    @Test
    void getSellerById() {
    }

    @Test
    @DisplayName("US-0008: Happy path ordenamiento ascendente")
    void orderByNameASCOkTest() {
        // Arrange
        NameSort order = NameSort.NAME_ASC;
        List<String> followerNamesExpected = Arrays.asList("Alice", "Bob", "Charlie");

        // Act
        SellerFollowersDto sellerFollowersActual = sellerService.OrderByName(sellerFollowers, order);
        List<String> followerNamesActual = extractNames(sellerFollowersActual.getFollowers());

        // Assert
        assertThat(followerNamesActual).containsExactlyElementsOf(followerNamesExpected);

    }

    @Test
    @DisplayName("US-0008: Happy path ordenamiento descendente")
    void orderByNameDESCOkTest() {
        // Arrange
        NameSort order = NameSort.NAME_DESC;
        List<String> followerNamesExpected = Arrays.asList("Charlie", "Bob", "Alice");

        // Act
        SellerFollowersDto sellerFollowersActual = sellerService.OrderByName(sellerFollowers, order);
        List<String> followerNamesActual = extractNames(sellerFollowersActual.getFollowers());

        // Assert
        assertThat(followerNamesActual).containsExactlyElementsOf(followerNamesExpected);
    }


    private List<String> extractNames(List<FollowerDto> followers) {
        return followers.stream()
                .map(FollowerDto::getUser_name)
                .toList();
    }
}