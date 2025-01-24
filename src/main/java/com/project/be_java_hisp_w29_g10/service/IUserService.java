package com.project.be_java_hisp_w29_g10.service;

import com.project.be_java_hisp_w29_g10.dto.ResponseMessageDto;
import com.project.be_java_hisp_w29_g10.entity.Seller;

import java.util.Optional;

public interface IUserService {
    ResponseMessageDto followSeller(Long userId, Long userIdToFollow);
    ResponseMessageDto unfollowSeller(Long userId, Long userIdToUnfollow);
    Optional<Seller> getSellerById(Long userId);
}
