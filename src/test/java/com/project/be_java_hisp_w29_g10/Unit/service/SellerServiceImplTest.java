package com.project.be_java_hisp_w29_g10.Unit.service;

import com.project.be_java_hisp_w29_g10.dto.response.FollowersCountDto;
import com.project.be_java_hisp_w29_g10.entity.Seller;
import com.project.be_java_hisp_w29_g10.exception.NotFoundException;
import com.project.be_java_hisp_w29_g10.repository.IFollowRepository;
import com.project.be_java_hisp_w29_g10.repository.ISellerRepository;
import com.project.be_java_hisp_w29_g10.service.SellerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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
        when(followRepository.getFollowersOfSeller(sellerId)).thenReturn(List.of());
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
    void orderByName() {
    }
}