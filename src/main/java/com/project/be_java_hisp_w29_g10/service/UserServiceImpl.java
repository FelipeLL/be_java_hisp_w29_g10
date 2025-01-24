package com.project.be_java_hisp_w29_g10.service;


import com.project.be_java_hisp_w29_g10.dto.request.response.ResponseMessageDto;
import com.project.be_java_hisp_w29_g10.entity.Follow;
import com.project.be_java_hisp_w29_g10.exception.ConflictException;
import com.project.be_java_hisp_w29_g10.exception.NotFoundException;
import com.project.be_java_hisp_w29_g10.repository.IFollowRepository;
import com.project.be_java_hisp_w29_g10.repository.ISellerRepository;
import com.project.be_java_hisp_w29_g10.repository.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService{
    private final IUserRepository userRepository;
    private final ISellerRepository sellerRepository;
    private final IFollowRepository followRepository;


    public UserServiceImpl(IUserRepository userRepository, ISellerRepository sellerRepository, IFollowRepository followRepository){
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.followRepository = followRepository;
    }

    @Override
    public ResponseMessageDto followSeller(Long userId, Long userIdToFollow) {
        if (userRepository.findById(userId).isEmpty()){throw new NotFoundException("No se encontró el usuario con el ID: "+userId);}
        if (sellerRepository.findById(userIdToFollow).isEmpty()){throw new NotFoundException("No se encontró el vendedor con el ID: "+userIdToFollow);}
        if (!followRepository.getFollowRelation(userId,userIdToFollow).isEmpty()){throw new ConflictException("El usuario con ID: "+userId+" seguir al vendedor con ID: "+userIdToFollow+", debido a que ya lo está siguiendo.");}

        Follow newFollow = followRepository.saveFollow(userId, userIdToFollow);
        return new ResponseMessageDto("El usuario "+newFollow.getUser_id()+" ahora sigue al vendedor "+newFollow.getSeller_id());
    }

    @Override
    public ResponseMessageDto unfollowSeller(Long userId, Long userIdToUnfollow) {
        Follow followRelation = followRepository.getFollowRelation(userId, userIdToUnfollow).get();
        if (userRepository.findById(userId).isEmpty()){ throw new NotFoundException("No se encontró el usuario con el ID: "+userId);}
        if (sellerRepository.findById(userIdToUnfollow).isEmpty()){ throw new NotFoundException("No se encontró el vendedor con el ID: "+userIdToUnfollow);}
        if (null == followRelation){ throw new NotFoundException("El usuario con ID: "+userId+" no puede dejar se seguir al vendedor con ID: "+userIdToUnfollow+", ya que no lo sigue.");}

        followRepository.removeFollow(followRelation);
        return new ResponseMessageDto("El usuario "+userId+" a dejado de seguir al vendedor "+userIdToUnfollow);
    }

    //Metodo para recuperar el nombre de un usuario(US3)
    @Override
    public String getUserName(Long userId) {
        return userRepository.findById(userId).get().getUser_name();
    }
}