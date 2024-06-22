package vn.codegym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.codegym.model.Posts;

@Repository
public interface IPostsRepository extends JpaRepository<Posts, Long> {

}
