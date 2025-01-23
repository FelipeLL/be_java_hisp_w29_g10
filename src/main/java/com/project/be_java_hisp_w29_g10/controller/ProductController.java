package com.project.be_java_hisp_w29_g10.controller;

import com.project.be_java_hisp_w29_g10.dto.request.PostRequestDto;
import com.project.be_java_hisp_w29_g10.service.IPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IPostService postService;

    public ProductController(IPostService postService){
        this.postService = postService;
    }

    @PostMapping("/post")
    public ResponseEntity<String> savePost(@RequestBody PostRequestDto postDto){
        postService.save(postDto);
        return new ResponseEntity<>("Publicación creada exitosamente.", HttpStatus.OK);
    }
}
