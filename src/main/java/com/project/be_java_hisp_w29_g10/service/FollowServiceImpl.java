package com.project.be_java_hisp_w29_g10.service;

import com.project.be_java_hisp_w29_g10.repository.IFollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements IFollowService {
    private final IFollowRepository followRepository;

    public FollowServiceImpl(IFollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    //Metodo para devolver la lista de vendedores que sigue de un usuario(US4)
    @Override
    public List<Long> getSellersFollowedByUser(Long id) {
        return followRepository.getSellersFollowedByUser(id);
    }
}
