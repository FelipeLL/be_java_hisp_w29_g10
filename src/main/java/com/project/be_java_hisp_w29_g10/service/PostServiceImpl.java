package com.project.be_java_hisp_w29_g10.service;

import com.project.be_java_hisp_w29_g10.dto.request.PostRequestDto;
import com.project.be_java_hisp_w29_g10.dto.request.ProductRequestDto;
import com.project.be_java_hisp_w29_g10.entity.Post;
import com.project.be_java_hisp_w29_g10.entity.Product;
import com.project.be_java_hisp_w29_g10.exception.BadRequestException;
import com.project.be_java_hisp_w29_g10.repository.IPostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class PostServiceImpl implements IPostService{

    private final IPostRepository postRepository;
    private final IProductService productService;

    public PostServiceImpl(IPostRepository postRepository, IProductService productService){
        this.postRepository = postRepository;
        this.productService = productService;
    }

    @Override
    public Post save(PostRequestDto postDto) {
        Random random = new Random();
        long postId;
        Optional<Post> existPost;
        Post newPost;
        Product newProduct = convertToProduct(postDto.getProduct());

        do {
            postId = random.nextLong(1000);
            existPost = postRepository.getById(postId);
        } while (existPost.isPresent());

        newPost = convertToPost(postDto, postId);

        productService.save(newProduct);

        return postRepository.save(newPost);
    }

    @Override
    public Post savePromo(PostRequestDto postRequestDto) {
        if (!postRequestDto.getHas_promo()){
            throw new BadRequestException("The post does not have a promo");
        }
        return save(postRequestDto);
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
                .discount(dto.getDiscount())
                .build();

    }
}
