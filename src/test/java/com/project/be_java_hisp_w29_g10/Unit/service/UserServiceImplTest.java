package com.project.be_java_hisp_w29_g10.Unit.service;

import com.project.be_java_hisp_w29_g10.dto.response.ResponseMessageDto;
import com.project.be_java_hisp_w29_g10.entity.Follow;
import com.project.be_java_hisp_w29_g10.entity.Seller;
import com.project.be_java_hisp_w29_g10.entity.User;
import com.project.be_java_hisp_w29_g10.exception.ConflictException;
import com.project.be_java_hisp_w29_g10.exception.NotFoundException;
import com.project.be_java_hisp_w29_g10.repository.IFollowRepository;
import com.project.be_java_hisp_w29_g10.repository.ISellerRepository;
import com.project.be_java_hisp_w29_g10.repository.IUserRepository;
import com.project.be_java_hisp_w29_g10.service.IFollowManagementService;
import com.project.be_java_hisp_w29_g10.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private IUserRepository userRepository;
    @Mock
    private ISellerRepository sellerRepository;
    @Mock
    private IFollowRepository followRepository;
    @Mock
    private IFollowManagementService followManagementService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("T0001 - US1 - Happy Path")
    void followSellerOkTest() {
        //arrange
        Long userId = 1L;
        Long userIdToFollow = 5L;
        ResponseMessageDto expectedMessage = new ResponseMessageDto("El usuario "+userId+" ahora sigue al vendedor "+userIdToFollow);

        when(followRepository.getFollowRelation(anyLong(),anyLong())).thenReturn(Optional.empty());
        when(followRepository.saveFollow(anyLong(),anyLong())).thenReturn(new Follow(userIdToFollow,userId));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User(1L, "test_user")));
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(new Seller(5L,"test_seller","+543492657482")));

        //act
        ResponseMessageDto foundMessage = userService.followSeller(userId, userIdToFollow);

        //assert
        assertEquals(expectedMessage, foundMessage);
    }
    @Test
    @DisplayName("T0001 - US1 - User Throw Not Found Exception")
    void followSellerThrowNotFoundExceptionUserTest() {
        //arrange
        Long userId = 1L;
        Long userIdToFollow = 5L;

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //act and assert
        assertThrows(NotFoundException.class, () -> userService.followSeller(userId, userIdToFollow));
    }
    @Test
    @DisplayName("T0001 - US1 - Seller Throw Not Found Exception")
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
    @DisplayName("T0001 - US1 - Throw Conflict Exception")
    void followSellerThrowConflictExceptionTest() {
        //arrange
        Long userId = 1L;
        Long userIdToFollow = 5L;

        when(followRepository.getFollowRelation(anyLong(),anyLong())).thenReturn(Optional.of(new Follow(userIdToFollow,userId)));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User(1L, "test_user")));
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(new Seller(5L,"test_seller","+543492657482")));

        //act and assert
        assertThrows(ConflictException.class, () -> userService.followSeller(userId, userIdToFollow));
    }

    @Test
    @DisplayName("T0002 - US7 - Happy Path")
    void unfollowSellerOkTest() {
        //arrange
        Long userId = 1L;
        Long userIdToUnfollow = 10L;
        Follow followRelation = new Follow(userIdToUnfollow, userId);
        ResponseMessageDto expectedMessage = new ResponseMessageDto("El usuario "+userId+" a dejado de seguir al vendedor "+userIdToUnfollow);

        when(followRepository.getFollowRelation(anyLong(),anyLong())).thenReturn(Optional.of(followRelation));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User(userId, "test_user")));
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(new Seller(userIdToUnfollow,"test_seller","+543492657482")));
        when(followRepository.removeFollow(any())).thenReturn(followRelation);

        //act
        ResponseMessageDto foundMessage = userService.unfollowSeller(userId, userIdToUnfollow);

        //assert
        assertEquals(expectedMessage, foundMessage);
    }

    @Test
    @DisplayName("T0002 - US7 - User Throw Not Found Exception")
    void unfollowSellerThrowNotFoundExceptionUserTest() {
        //arrange
        Long userId = 1L;
        Long userIdToUnfollow = 10L;

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //act and assert
        assertThrows(NotFoundException.class, () -> userService.unfollowSeller(userId, userIdToUnfollow));
    }

    @Test
    @DisplayName("T0002 - US7 - Seller Throw Not Found Exception")
    void unfollowSellerThrowNotFoundExceptionSellerTest() {
        //arrange
        Long userId = 1L;
        Long userIdToUnfollow = 10L;

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User(userId, "test_user")));
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.empty());

        //act and assert
        assertThrows(NotFoundException.class, () -> userService.unfollowSeller(userId, userIdToUnfollow));
    }

    @Test
    @DisplayName("T0002 - US7 - Throw Not Found Exception")
    void unfollowSellerThrowNotFoundExceptionTest() {
        //arrange
        Long userId = 1L;
        Long userIdToUnfollow = 10L;

        when(followRepository.getFollowRelation(anyLong(),anyLong())).thenReturn(Optional.empty());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User(userId, "test_user")));
        when(sellerRepository.findById(anyLong())).thenReturn(Optional.of(new Seller(userIdToUnfollow,"test_seller","+543492657482")));

        //act and assert
        assertThrows(NotFoundException.class, () -> userService.unfollowSeller(userId, userIdToUnfollow));
    }

}