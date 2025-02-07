package com.project.be_java_hisp_w29_g10.Unit.service;

import com.project.be_java_hisp_w29_g10.dto.response.FollowerDto;
import com.project.be_java_hisp_w29_g10.dto.response.FollowersCountDto;
import com.project.be_java_hisp_w29_g10.dto.response.SellerFollowersDto;
import com.project.be_java_hisp_w29_g10.entity.Seller;
import com.project.be_java_hisp_w29_g10.enums.NameOrderType;
import com.project.be_java_hisp_w29_g10.exception.NotFoundException;
import com.project.be_java_hisp_w29_g10.repository.IFollowRepository;
import com.project.be_java_hisp_w29_g10.repository.ISellerRepository;
import com.project.be_java_hisp_w29_g10.service.SellerServiceImpl;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellerServiceImplTest {
    @Mock
    IFollowRepository followRepository;

    @Mock
    ISellerRepository sellerRepository;

    @InjectMocks
    SellerServiceImpl sellerService;

    SellerFollowersDto sellerFollowers;

    @BeforeEach
    void setUp() {
        FollowerDto follower1 = new FollowerDto(101L, "Alice");
        FollowerDto follower2 = new FollowerDto(102L, "Bob");
        FollowerDto follower3 = new FollowerDto(103L, "Charlie");

        sellerFollowers = new SellerFollowersDto(100L, "Juan Pablo", Arrays.asList(follower2, follower3, follower1));
    }

    @Test
    @DisplayName("US2 - Happy path")
    void getCountFollowersOkTest() {
        //arrange
        Seller seller = new Seller();
        Long sellerId = 1L;
        seller.setId_seller(sellerId);
        seller.setUser_name("Erik Calvillo");
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(seller));
        when(followRepository.getFollowersOfSeller(sellerId)).thenReturn(List.of(1L, 2L, 3L));
        //act
        FollowersCountDto result = sellerService.getCountFollowers(sellerId);
        //assert
        assertEquals(sellerId, result.getUser_id());
        assertEquals("Erik Calvillo", result.getUser_name());
        assertEquals(3, result.getFollow_count());
    }

    @Test
    @DisplayName("US2 - Throw Not Found Exception")
    void getCountFollowersThrowsNotFoundExceptionTest() {
        //arrange
        Long sellerId = 1L;
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());
        //act & assert
        assertThrows(NotFoundException.class, () -> sellerService.getCountFollowers(sellerId));
    }

    @Test
    void getSellerAndFollowers() {
    }

    @Test
    void getSellerById() {
    }

    @Test
    @DisplayName("US8 - Happy path ordenamiento ascendente")
    void orderByNameASCOkTest() {
        // Arrange
        NameOrderType order = NameOrderType.NAME_ASC;
        List<String> followerNamesExpected = Arrays.asList("Alice", "Bob", "Charlie");

        // Act
        SellerFollowersDto sellerFollowersActual = sellerService.OrderByName(sellerFollowers, order);
        List<String> followerNamesActual = extractNames(sellerFollowersActual.getFollowers());

        // Assert
        assertThat(followerNamesActual).containsExactlyElementsOf(followerNamesExpected);

    }

    @Test
    @DisplayName("US8 - Happy path ordenamiento descendente")
    void orderByNameDESCOkTest() {
        // Arrange
        NameOrderType order = NameOrderType.NAME_DESC;
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