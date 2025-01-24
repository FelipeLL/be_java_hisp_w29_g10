package com.project.be_java_hisp_w29_g10.repository;

import com.project.be_java_hisp_w29_g10.entity.Post;

import java.util.List;
import java.util.Optional;

public interface IPostRepository {
    Optional<Post> getById(Long id);
    Post save(Post newPost);

    List<Post> getPromoPostBySellerID(Long userId);
}
