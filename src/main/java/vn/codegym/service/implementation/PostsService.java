package vn.codegym.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;
import vn.codegym.model.posts.Posts;
import vn.codegym.model.posts.PostsDTO;
import vn.codegym.repository.IPostsRepository;
import vn.codegym.service.IPostsService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static vn.codegym.controller.PostsController.IMAGE_FILE_INVALID_MESSAGE;

@Service
public class PostsService implements IPostsService {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IPostsRepository iPostsRepository;

    @Override
    public Iterable<Posts> findAll() {
        return iPostsRepository.findAll();
    }

    @Override
    public Page<Posts> findAll(Pageable pageable) {
        return iPostsRepository.findAll(pageable);
    }

    @Override
    public void save(Posts posts) {
        iPostsRepository.save(posts);
    }

    @Override
    public Optional<Posts> findById(Long id) {
        return iPostsRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iPostsRepository.deleteById(id);
        ;
    }

    public void savePost(PostsDTO postsDTO, String fileName) {
        Posts posts = getPostsFromDTO(postsDTO, fileName);
        save(posts);
    }

    public static Posts getPosts(PostsDTO postsDTO, String fileName) {
        Posts posts = new Posts();
        posts.setContent(postsDTO.getContent());
        posts.setTitle(postsDTO.getTitle());
        posts.setShortDescription(postsDTO.getShortDescription());
        posts.setImageFileName(fileName);
        return posts;
    }

    public void saveAnCopyOfFileToStorage(PostsDTO postsDTO, String fileName) throws IOException {
        FileCopyUtils.copy(postsDTO.getImageFile().getBytes(), new File(fileUpload + fileName));
    }

    @Override
    public Posts getPostsFromDTO(PostsDTO postsDTO, String fileName) {
        Posts posts = new Posts();
        posts.setContent(postsDTO.getContent());
        posts.setTitle(postsDTO.getTitle());
        posts.setShortDescription(postsDTO.getShortDescription());
        posts.setImageFileName(fileName);
        posts.setCategory(postsDTO.getCategory());
        return posts;
    }

    @Override
    public String getFileName(PostsDTO postsDTO) {
        MultipartFile multipartFile = postsDTO.getImageFile();
        String fileName = multipartFile.getOriginalFilename();
        return fileName;
    }

    @Override
    public void checkUploadImageInvalid(PostsDTO postsDTO, BindingResult bindingResult) {
        if (postsDTO.getImageFile().isEmpty())
            bindingResult.addError(new FieldError("postsDTO", "imageFile", IMAGE_FILE_INVALID_MESSAGE));
    }

    @Override
    public Page<Posts> findAllByTitle(Pageable pageable, String name) {
        return iPostsRepository.findByTitleContaining(pageable, name);
    }

    @Override
    public Page<Posts> getPostsByTitleContaining(Pageable pageable, String title) {
        return iPostsRepository.getPostByTitleContaining(pageable,title);
    }
}
