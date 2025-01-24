package com.project.be_java_hisp_w29_g10.controller;

import com.project.be_java_hisp_w29_g10.dto.request.PostRequestDto;
import com.project.be_java_hisp_w29_g10.dto.request.response.PromoPostCountDto;
import com.project.be_java_hisp_w29_g10.dto.request.response.RecentPostsResponseDto;
import com.project.be_java_hisp_w29_g10.service.IPostService;
import com.project.be_java_hisp_w29_g10.service.IProductService;
import com.project.be_java_hisp_w29_g10.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final IPostService postService;
    private final IUserService userService;

    public ProductController(IPostService postService, IUserService userService){
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/post")
    public ResponseEntity<String> savePost(@RequestBody PostRequestDto postDto){
        postService.save(postDto);
        return new ResponseEntity<>("Publicación creada exitosamente.", HttpStatus.OK);
    }
    @PostMapping("/promo-post")
    public ResponseEntity<String> savePromoPost(@RequestBody PostRequestDto postRequestDto){
        postService.savePromo(postRequestDto);
        return new ResponseEntity<>("Publicación con promo creada exitosamente.", HttpStatus.OK);
    }
    @GetMapping("/promo-post/count")
    public ResponseEntity<PromoPostCountDto> getPromoPost(@RequestParam Long user_id){
        PromoPostCountDto countDto = postService.getPromoPostCountBySellerId(user_id);
        return new ResponseEntity<>(countDto, HttpStatus.OK);
    }
    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> getRecentPostsByFollowedSellers(@PathVariable Long userId) {
        RecentPostsResponseDto response = postService.getRecentPostsByFollowedSellers(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
