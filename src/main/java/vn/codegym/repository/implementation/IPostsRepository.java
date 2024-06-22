package vn.codegym.repository.implementation;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.codegym.model.Posts;


public interface IPostsRepository extends JpaRepository<Posts, Long> {
}
