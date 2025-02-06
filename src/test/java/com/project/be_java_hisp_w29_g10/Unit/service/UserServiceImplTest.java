package com.project.be_java_hisp_w29_g10.Unit.service;

import com.project.be_java_hisp_w29_g10.dto.response.FollowedSellerDto;
import com.project.be_java_hisp_w29_g10.dto.response.FollowerDto;
import com.project.be_java_hisp_w29_g10.dto.response.SellerFollowersDto;
import com.project.be_java_hisp_w29_g10.dto.response.UserFollowedSellerDto;
import com.project.be_java_hisp_w29_g10.enums.NameSort;
import com.project.be_java_hisp_w29_g10.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    UserFollowedSellerDto userFollowedSellers;

    @InjectMocks
    UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        FollowedSellerDto follower1 = new FollowedSellerDto(101L, "Alice");
        FollowedSellerDto follower2 = new FollowedSellerDto(102L, "Bob");
        FollowedSellerDto follower3 = new FollowedSellerDto(103L, "Charlie");

        userFollowedSellers = new UserFollowedSellerDto(100L, "Juan Pablo", Arrays.asList(follower2, follower3, follower1));
    }

    @Test
    void followSeller() {
    }

    @Test
    void unfollowSeller() {
    }

    @Test
    void getUserAndFollowedSellers() {
    }

    @Test
    @DisplayName("US-0008: Happy path ordenamiento ascendente")
    void orderByNameASCOkTest() {
        // Arrange
        NameSort order = NameSort.NAME_ASC;
        List<String> followedSellerNamesExpected = Arrays.asList("Alice", "Bob", "Charlie");

        // Act
        UserFollowedSellerDto userFollowedSellersActual = userService.OrderByName(userFollowedSellers, order);
        List<String> followerNamesActual = extractNames(userFollowedSellersActual.getFollowed());

        // Assert
        assertThat(followerNamesActual).containsExactlyElementsOf(followedSellerNamesExpected);

    }

    @Test
    @DisplayName("US-0008: Happy path ordenamiento descendente")
    void orderByNameDESCOkTest() {
        // Arrange
        NameSort order = NameSort.NAME_DESC;
        List<String> followedSellerNamesExpected = Arrays.asList("Charlie", "Bob", "Alice");

        // Act
        UserFollowedSellerDto userFollowedSellersActual = userService.OrderByName(userFollowedSellers, order);
        List<String> followerNamesActual = extractNames(userFollowedSellersActual.getFollowed());

        // Assert
        assertThat(followerNamesActual).containsExactlyElementsOf(followedSellerNamesExpected);

    }

    private List<String> extractNames(List<FollowedSellerDto> followers) {
        return followers.stream()
                .map(FollowedSellerDto::getUser_name)
                .toList();
    }
}