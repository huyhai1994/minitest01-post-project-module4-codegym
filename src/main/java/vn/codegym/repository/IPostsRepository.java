package vn.codegym.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.codegym.model.posts.Posts;

import java.util.List;

@Repository
public interface IPostsRepository extends CrudRepository<Posts, Long> {
    Page<Posts> findAll(Pageable pageable);


    Page<Posts> findByTitleContaining(Pageable pageable, String name);

    @Query(value = "select * from posts where title like %?1%", nativeQuery = true)
    Page<Posts> getPostByTitleContaining(Pageable pageable, String title);
}
