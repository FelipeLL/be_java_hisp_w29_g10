package com.project.be_java_hisp_w29_g10.repository;

import com.project.be_java_hisp_w29_g10.repository.IProductRepository;
import com.project.be_java_hisp_w29_g10.entity.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Repository
public class PostRepositoryImpl implements IPostRepository {
    List<Post> posts;

    public PostRepositoryImpl(){
        posts = new ArrayList<>(List.of(
                new Post(1L, 1L, LocalDate.parse("2025-01-01"), 1L, 1, 1200.00, true, 200.00),
                new Post(2L, 2L, LocalDate.parse("2025-01-02"), 2L, 1, 999.99, false, 0.00),
                new Post(3L, 3L, LocalDate.parse("2025-01-03"), 3L, 2, 1500.50, true, 300.50),
                new Post(4L, 4L, LocalDate.parse("2025-01-04"), 4L, 3, 19.99, true, 5.00),
                new Post(5L, 5L, LocalDate.parse("2025-01-05"), 5L, 3, 89.99, false, 0.00),
                new Post(6L, 6L, LocalDate.parse("2025-01-06"), 6L, 4, 199.99, true, 50.00),
                new Post(7L, 7L, LocalDate.parse("2025-01-07"), 7L, 1, 299.99, false, 0.00),
                new Post(8L, 8L, LocalDate.parse("2025-01-08"), 8L, 5, 149.99, true, 20.00),
                new Post(9L, 9L, LocalDate.parse("2025-01-09"), 9L, 4, 79.99, true, 15.00),
                new Post(10L, 10L, LocalDate.parse("2025-01-10"), 10L, 5, 199.99, false, 0.00)
        ));

    }

    @Override
    public Post save(Post newPost) {
         posts.add(newPost);
         return newPost;
    }
}
