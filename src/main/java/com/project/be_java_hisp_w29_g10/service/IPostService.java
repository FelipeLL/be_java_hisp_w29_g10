package com.project.be_java_hisp_w29_g10.service;


import com.project.be_java_hisp_w29_g10.dto.request.PostRequestDto;
import com.project.be_java_hisp_w29_g10.dto.request.response.PromoPostCountDto;
import com.project.be_java_hisp_w29_g10.dto.request.response.RecentPostsResponseDto;
import com.project.be_java_hisp_w29_g10.entity.Post;

public interface IPostService {
    Post save(PostRequestDto postDto);
    Post savePromo(PostRequestDto postRequestDto);

    PromoPostCountDto getPromoPostCountBySellerId(Long userId);
    RecentPostsResponseDto getRecentPostsByFollowedSellers(Long userId);

}
