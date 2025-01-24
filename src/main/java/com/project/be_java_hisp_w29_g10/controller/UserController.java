package com.project.be_java_hisp_w29_g10.controller;

import com.project.be_java_hisp_w29_g10.dto.request.response.FollowersCountDto;
import com.project.be_java_hisp_w29_g10.dto.request.response.ResponseMessageDto;
import com.project.be_java_hisp_w29_g10.dto.request.response.SellerFollowersDto;
import com.project.be_java_hisp_w29_g10.service.ISellerService;
import com.project.be_java_hisp_w29_g10.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    private final ISellerService sellerService;

    public UserController(IUserService userService, ISellerService sellerService) {
        this.userService = userService;
        this.sellerService = sellerService;
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<ResponseMessageDto> followSeller(@PathVariable Long userId, @PathVariable Long userIdToFollow) {
        return ResponseEntity.ok(userService.followSeller(userId, userIdToFollow));
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<ResponseMessageDto> unfollowSeller(@PathVariable Long userId, @PathVariable Long userIdToUnfollow) {
        return ResponseEntity.ok(userService.unfollowSeller(userId, userIdToUnfollow));
    }

    //ENDPOINT PARA OBTENER LA CANTIDAD DE SEGUIDORES DE UN VENDEDOR(US 02)
    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<FollowersCountDto> getCountFollowers(@PathVariable Long userId) {
        return new ResponseEntity<>(sellerService.getCountFollowers(userId), HttpStatus.OK);
    }

    //ENDPOINT PARA OBTENER A UN VENDEDOR Y SU LISTA DE SEGUIDORES(US 03)
    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<SellerFollowersDto> getSellerAndFollowers(@PathVariable Long userId) {
        return new ResponseEntity<>(sellerService.getSellerAndFollowers(userId), HttpStatus.OK);
    }
}
