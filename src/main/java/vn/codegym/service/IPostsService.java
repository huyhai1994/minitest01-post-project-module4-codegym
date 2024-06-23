package vn.codegym.service;

import org.springframework.validation.BindingResult;
import vn.codegym.model.posts.Posts;
import vn.codegym.model.posts.PostsDTO;

import java.io.IOException;

public interface IPostsService extends IGenerateService<Posts> {
    void savePost(PostsDTO postsDTO, String fileName);

    void saveAnCopyOfFileToStorage(PostsDTO postsDTO, String fileName) throws IOException;

    Posts getPostsFromDTO(PostsDTO postsDTO, String fileName);

    String getFileName(PostsDTO postsDTO);

    void checkUploadImageInvalid(PostsDTO postsDTO, BindingResult bindingResult);



}
