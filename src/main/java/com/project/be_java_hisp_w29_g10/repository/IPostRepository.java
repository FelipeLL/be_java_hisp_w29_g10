package com.project.be_java_hisp_w29_g10.repository;

import com.project.be_java_hisp_w29_g10.entity.Post;

public interface IPostRepository {
    Post save(Post newPost);
}
