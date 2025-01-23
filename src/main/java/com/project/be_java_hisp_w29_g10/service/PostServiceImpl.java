package com.project.be_java_hisp_w29_g10.service;

import com.project.be_java_hisp_w29_g10.dto.request.PostRequestDto;
import com.project.be_java_hisp_w29_g10.dto.request.ProductRequestDto;
import com.project.be_java_hisp_w29_g10.entity.Post;
import com.project.be_java_hisp_w29_g10.entity.Product;
import com.project.be_java_hisp_w29_g10.repository.IPostRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PostServiceImpl implements IPostService{

    private final IPostRepository postRepository;

    public PostServiceImpl(IPostRepository postRepository){
        this.postRepository = postRepository;
    }

    @Override
    public Post save(PostRequestDto postDto) {


        Product newProduct = convertToProduct(postDto.getProduct());

        Random random = new Random();
        Long postId = random.nextLong(1000);

        // save product

        // save post
        Post newPost = convertToPost(postDto, postId);
        return postRepository.save(newPost);
    }

    public Product convertToProduct(ProductRequestDto dto){
        return Product.builder()
                .product_id(dto.getProduct_id())
                .product_name(dto.getProduct_name())
                .type(dto.getType())
                .brand(dto.getBrand())
                .color(dto.getColor())
                .notes(dto.getNotes())
                .build();
    }

    public Post convertToPost(PostRequestDto dto, Long postId){
        return Post.builder()
                .post_id(postId)
                .user_id(dto.getUser_id())
                .date(dto.getDate())
                .product_id(dto.getProduct().getProduct_id())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .has_promo(dto.getHas_promo())
                .build();

    }
}
