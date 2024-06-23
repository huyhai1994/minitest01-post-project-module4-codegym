package vn.codegym.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.codegym.model.posts.Posts;

@Repository
public interface IPostsRepository extends CrudRepository<Posts, Long> {
    Page<Posts> findAll(Pageable pageable);

    Page<Posts> findByTitleContaining(Pageable pageable, String name);
}
