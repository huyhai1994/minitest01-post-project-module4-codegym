package vn.codegym.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.codegym.model.posts.Posts;

@Repository
public interface IPostsRepository extends CrudRepository<Posts, Long> {

}
