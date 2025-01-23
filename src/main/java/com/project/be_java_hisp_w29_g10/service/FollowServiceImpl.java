package com.project.be_java_hisp_w29_g10.service;

import com.project.be_java_hisp_w29_g10.repository.IFollowRepository;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements IFollowService {
    private final IFollowRepository followRepository;

    public FollowServiceImpl(IFollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    //Metodo para devolver la cantidad de seguidores de un vendedor(US2)
    @Override
    public Integer getCountFollowers(Long sellerId) {
        return followRepository.getFollowersOfSeller(sellerId).size();
    }

}
