package com.project.be_java_hisp_w29_g10.service;

import com.project.be_java_hisp_w29_g10.dto.request.response.FollowersCountDto;
import com.project.be_java_hisp_w29_g10.entity.Seller;
import com.project.be_java_hisp_w29_g10.exception.NotFoundException;
import com.project.be_java_hisp_w29_g10.repository.ISellerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerServiceImpl implements ISellerService{
    private final IFollowService followService;
    private final ISellerRepository sellerRepository;

    public SellerServiceImpl(IFollowService followService, ISellerRepository sellerRepository) {
        this.followService = followService;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public FollowersCountDto getCountFollowers(Long sellerId) {
        if(followService.getCountFollowers(sellerId) == 0) {
            throw new NotFoundException("No se encontro el vendedor con el ID: "+sellerId);
        }
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        return new FollowersCountDto (seller.get().getId_seller(),seller.get().getUser_name(),followService.getCountFollowers(sellerId));
    }
}
