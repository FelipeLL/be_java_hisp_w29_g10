package com.project.be_java_hisp_w29_g10.service;

import com.project.be_java_hisp_w29_g10.dto.request.PostRequestDto;
import com.project.be_java_hisp_w29_g10.dto.request.ProductRequestDto;
import com.project.be_java_hisp_w29_g10.dto.request.response.PromoPostCountDto;
import com.project.be_java_hisp_w29_g10.entity.Post;
import com.project.be_java_hisp_w29_g10.entity.Product;
import com.project.be_java_hisp_w29_g10.entity.Seller;
import com.project.be_java_hisp_w29_g10.exception.BadRequestException;
import com.project.be_java_hisp_w29_g10.exception.NotFoundException;
import com.project.be_java_hisp_w29_g10.repository.IPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PostServiceImpl implements IPostService{

    private final IPostRepository postRepository;
    private final IProductService productService;
    private final ISellerService sellerService;

    public PostServiceImpl(IPostRepository postRepository, IProductService productService, ISellerService sellerService) {
        this.postRepository = postRepository;
        this.productService = productService;
        this.sellerService = sellerService;
    }

    @Override
    public Post save(PostRequestDto postDto) {
        Optional<Seller> seller = sellerService.getSellerById(postDto.getUser_id());
        if(seller.isEmpty()) {
            throw new NotFoundException("Vendedor con la id: " + postDto.getUser_id() + " no encontrado");
        }
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
            throw new BadRequestException("La publicación no tiene promo");
        }
        return save(postRequestDto);
    }

    @Override
    public PromoPostCountDto getPromoPostCountBySellerId(Long userId) {
        Optional<Seller> seller = sellerService.getSellerById(userId);
        if (seller.isEmpty()) {
            throw new NotFoundException("Vendedor con la id: " + userId+ " no encontrado");
        }
        List<Post> posts = postRepository.getPromoPostBySellerID(userId);
        PromoPostCountDto promoPostCountDto = new PromoPostCountDto();
        promoPostCountDto.setUser_id(userId);
        promoPostCountDto.setUser_name(seller.get().getUser_name());
        promoPostCountDto.setPromo_products_count((long) posts.size());
        return promoPostCountDto;
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
