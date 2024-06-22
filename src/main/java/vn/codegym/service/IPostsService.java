package vn.codegym.service;

import org.springframework.stereotype.Service;
import vn.codegym.model.posts.Posts;

@Service
public interface IPostsService extends IGenerateService<Posts> {
}
