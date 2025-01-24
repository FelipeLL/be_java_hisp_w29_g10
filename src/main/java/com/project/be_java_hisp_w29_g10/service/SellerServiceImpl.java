package com.project.be_java_hisp_w29_g10.service;

import com.project.be_java_hisp_w29_g10.dto.request.response.FollowerDto;
import com.project.be_java_hisp_w29_g10.dto.request.response.FollowersCountDto;
import com.project.be_java_hisp_w29_g10.dto.request.response.SellerFollowersDto;
import com.project.be_java_hisp_w29_g10.entity.Seller;
import com.project.be_java_hisp_w29_g10.exception.NotFoundException;
import com.project.be_java_hisp_w29_g10.repository.ISellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements ISellerService{
    private final IFollowService followService;
    private final ISellerRepository sellerRepository;
    private final UserServiceImpl userServiceImpl;

    public SellerServiceImpl(IFollowService followService, ISellerRepository sellerRepository, UserServiceImpl userServiceImpl) {
        this.followService = followService;
        this.sellerRepository = sellerRepository;
        this.userServiceImpl = userServiceImpl;
    }

    //Metodo para devolver la cantidad de seguidores de un vendedor(US2)
    @Override
    public FollowersCountDto getCountFollowers(Long sellerId) {
        if(followService.getFollowersOfSeller(sellerId).isEmpty()) {
            throw new NotFoundException("No se encontro el vendedor con el ID: "+sellerId);
        }
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        return new FollowersCountDto (seller.get().getId_seller(),seller.get().getUser_name(),followService.getFollowersOfSeller(sellerId).size());
    }

    //Metodo para devolver al vendedor y su lista de seguidores(US3)
    @Override
    public SellerFollowersDto getSellerAndFollowers(Long sellerId) {
        if(followService.getFollowersOfSeller(sellerId).isEmpty()) {
            throw new NotFoundException("No se encontro el vendedor con el ID: "+sellerId);
        }
        List<Long> followers = followService.getFollowersOfSeller(sellerId);
        Optional<Seller> seller = sellerRepository.findById(sellerId);
        List<FollowerDto> followerDtos = followers.stream()
                .map(followerId -> new FollowerDto(followerId, userServiceImpl.getUserName(followerId)))
                .toList();
        return new SellerFollowersDto(seller.get().getId_seller(), seller.get().getUser_name(), followerDtos);
    }
    @Override
    public Optional<Seller> getSellerById(Long userId) {
        return sellerRepository.findById(userId);
    }
}
