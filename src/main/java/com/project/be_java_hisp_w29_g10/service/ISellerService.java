package com.project.be_java_hisp_w29_g10.service;

import com.project.be_java_hisp_w29_g10.dto.request.response.FollowersCountDto;

public interface ISellerService {
    FollowersCountDto getCountFollowers(Long sellerId);
}
