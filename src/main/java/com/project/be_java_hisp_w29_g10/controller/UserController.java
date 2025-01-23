package com.project.be_java_hisp_w29_g10.controller;

import com.project.be_java_hisp_w29_g10.dto.ResponseMessageDto;
import com.project.be_java_hisp_w29_g10.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService){
        this.userService = userService;
    }

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<ResponseMessageDto> followSeller(@PathVariable Long userId, @PathVariable Long userIdToFollow){
        return ResponseEntity.ok(userService.followSeller(userId, userIdToFollow));
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<ResponseMessageDto> unfollowSeller(@PathVariable Long userId, @PathVariable Long userIdToUnfollow){
        return ResponseEntity.ok(userService.unfollowSeller(userId, userIdToUnfollow));
    }
}
