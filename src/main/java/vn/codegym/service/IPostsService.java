package vn.codegym.service;

import org.springframework.stereotype.Service;
import vn.codegym.model.posts.Posts;
import vn.codegym.model.posts.PostsDTO;

@Service
public interface IPostsService extends IGenerateService<Posts> {
    void savePost(PostsDTO postsDTO, String fileName);
}
