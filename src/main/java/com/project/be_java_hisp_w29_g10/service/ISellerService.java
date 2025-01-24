package com.project.be_java_hisp_w29_g10.service;

import com.project.be_java_hisp_w29_g10.dto.request.response.FollowersCountDto;
import com.project.be_java_hisp_w29_g10.dto.request.response.SellerFollowersDto;

public interface ISellerService {
    //Metodo para devolver la cantidad de seguidores de un vendedor(US2)
    FollowersCountDto getCountFollowers(Long sellerId);
    //Metodo para mostrar el vendedor y su lista de seguidores(US3)
    SellerFollowersDto getSellerAndFollowers(Long sellerId);
}
