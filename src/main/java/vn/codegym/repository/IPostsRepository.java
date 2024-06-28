package vn.codegym.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import vn.codegym.model.posts.Posts;

import java.util.List;

public interface IPostsRepository extends CrudRepository<Posts, Long> {
    Page<Posts> findAll(Pageable pageable);


    Page<Posts> findByTitleContaining(Pageable pageable, String name);

    @Query(value = "SELECT * FROM posts WHERE title LIKE %?1%", nativeQuery = true)
    Page<Posts> getPostByTitleContaining(Pageable pageable, String title);


}
