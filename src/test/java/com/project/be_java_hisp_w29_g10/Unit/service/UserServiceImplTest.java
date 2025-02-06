package com.project.be_java_hisp_w29_g10.Unit.service;

import com.project.be_java_hisp_w29_g10.dto.response.FollowedSellerDto;
import com.project.be_java_hisp_w29_g10.dto.response.ResponseMessageDto;
import com.project.be_java_hisp_w29_g10.dto.response.UserFollowedSellerDto;
import com.project.be_java_hisp_w29_g10.entity.Follow;
import com.project.be_java_hisp_w29_g10.entity.Seller;
import com.project.be_java_hisp_w29_g10.entity.User;
import com.project.be_java_hisp_w29_g10.enums.NameSort;
import com.project.be_java_hisp_w29_g10.exception.ConflictException;
import com.project.be_java_hisp_w29_g10.exception.NotFoundException;
import com.project.be_java_hisp_w29_g10.repository.IFollowRepository;
import com.project.be_java_hisp_w29_g10.repository.ISellerRepository;
import com.project.be_java_hisp_w29_g10.repository.IUserRepository;
import com.project.be_java_hisp_w29_g10.service.IFollowManagementService;
import com.project.be_java_hisp_w29_g10.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    UserFollowedSellerDto userFollowedSellers;

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private ISellerRepository sellerRepository;
    @Mock
    private IFollowRepository followRepository;
    @Mock
    private IFollowManagementService followManagementService;

    @BeforeEach
    void setUp() {
        FollowedSellerDto follower1 = new FollowedSellerDto(101L, "Alice");
        FollowedSellerDto follower2 = new FollowedSellerDto(102L, "Bob");
        FollowedSellerDto follower3 = new FollowedSellerDto(103L, "Charlie");

        userFollowedSellers = new UserFollowedSellerDto(100L, "Juan Pablo", Arrays.asList(follower2, follower3, follower1));
    }

    @Test
    @DisplayName("US1 - Happy Path")
    void followSellerOkTest() {
        //arrange
        Long userId = 1L;
        Long userIdToFollow = 5L;
        ResponseMessageDto expectedMessage = new ResponseMessageDto("El usuario " + userId + " ahora sigue al vendedor " + userIdToFollow);

        when(followRepository.getFollowRelation(anyLong(), anyLong())).thenReturn(Optional.empty());
        when(followRepository.saveFollow(anyLong(), anyLong())).thenReturn(new Follow(userIdToFollow, userId));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User(1L, "test_user")));
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(new Seller(5L, "test_seller", "+543492657482")));

        //act
        ResponseMessageDto foundMessage = userService.followSeller(userId, userIdToFollow);

        //assert
        assertEquals(expectedMessage, foundMessage);
    }

    @Test
    @DisplayName("US1 - User Throw Not Found Exception")
    void followSellerThrowNotFoundExceptionUserTest() {
        //arrange
        Long userId = 1L;
        Long userIdToFollow = 5L;

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //act and assert
        assertThrows(NotFoundException.class, () -> userService.followSeller(userId, userIdToFollow));
    }

    @Test
    @DisplayName("US1 - Seller Throw Not Found Exception")
    void followSellerThrowNotFoundExceptionSellerTest() {
        //arrange
        Long userId = 1L;
        Long userIdToFollow = 5L;

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User(1L, "test_user")));
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.empty());

        //act and assert
        assertThrows(NotFoundException.class, () -> userService.followSeller(userId, userIdToFollow));
    }

    @Test
    @DisplayName("US1 - Throw Conflict Exception")
    void followSellerThrowConflictExceptionTest() {
        //arrange
        Long userId = 1L;
        Long userIdToFollow = 5L;

        when(followRepository.getFollowRelation(anyLong(), anyLong())).thenReturn(Optional.of(new Follow(userIdToFollow, userId)));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User(1L, "test_user")));
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(new Seller(5L, "test_seller", "+543492657482")));

        //act and assert
        assertThrows(ConflictException.class, () -> userService.followSeller(userId, userIdToFollow));
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