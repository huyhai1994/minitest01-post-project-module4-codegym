package vn.codegym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import vn.codegym.model.posts.Posts;
import vn.codegym.model.posts.PostsDTO;
import vn.codegym.service.IPostsService;
import vn.codegym.uri.PostsRequestUri;
import vn.codegym.uri.PostsViewUri;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;


@RequestMapping("/posts")
@Controller
public class PostsController {

    public static final String IMAGE_FILE_INVALID_MESSAGE = "can them anh vao";
    @Autowired
    private IPostsService iPostsService;

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping({PostsRequestUri.BLANK, PostsRequestUri.SLASH})
    public ModelAndView index(ModelAndView modelAndView) {
        List<Posts> posts = iPostsService.findAll(Sort.by(Sort.Direction.DESC, "id"));
        modelAndView.addObject("posts", posts);
        modelAndView.setViewName(PostsViewUri.POSTS_INDEX);
        return modelAndView;
    }

    @GetMapping(PostsRequestUri.CREATE)
    public ModelAndView showCreateForm(ModelAndView modelAndView) {
        PostsDTO postsDTO = new PostsDTO();
        modelAndView.addObject("postsDTO", postsDTO);
        modelAndView.setViewName(PostsViewUri.POSTS_CREATE);
        return modelAndView;
    }

    @PostMapping(PostsRequestUri.CREATE)
    public String createPost(@Valid @ModelAttribute PostsDTO postsDTO, BindingResult bindingResult) {
        /*TODO: @Valid, BindingResult -> check validation of fields*/
        checkUploadImageInvalid(postsDTO, bindingResult);
        if (isBindingError(bindingResult)) return PostsViewUri.POSTS_CREATE;
        String fileName = getFileName(postsDTO);
        try {
            saveAnCopyOfFileToStorage(postsDTO, fileName);
            savePost(postsDTO, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PostsViewUri.REDIRECT_TO_POSTS;
    }

    private void saveAnCopyOfFileToStorage(PostsDTO postsDTO, String fileName) throws IOException {
        FileCopyUtils.copy(postsDTO.getImageFile().getBytes(), new File(fileUpload + fileName));
    }

    @GetMapping(PostsRequestUri.EDIT)
    public String showEditPage(Model model, @PathVariable Long id) {
        Posts posts = iPostsService.findById(id);
        model.addAttribute("posts", posts);
        PostsDTO postsDTO = new PostsDTO();
        postsDTO.setTitle(posts.getTitle());
        postsDTO.setContent(posts.getContent());
        postsDTO.setShortDescription(posts.getShortDescription());
        model.addAttribute("postsDTO", postsDTO);
        return PostsViewUri.POSTS_EDIT;
    }

    @PostMapping(PostsRequestUri.EDIT)
    public String editPost(@Valid @ModelAttribute PostsDTO postsDTO, BindingResult bindingResult, @PathVariable Long id) {
        /*TODO:
         *   1. Phai kiem tra xem hinh anh da ton tai trong CSDL chua?
         *   2. edit hinh anh thi phai xoa anh cu
         *   3. luu anh moi vao storage/images*/

        checkUploadImageInvalid(postsDTO, bindingResult);
        if (isBindingError(bindingResult)) return PostsViewUri.POSTS_EDIT;
        Posts posts = iPostsService.findById(id);
        posts.setTitle(postsDTO.getTitle());
        posts.setContent(postsDTO.getContent());
        posts.setShortDescription(postsDTO.getShortDescription());
        iPostsService.save(posts);
        return PostsViewUri.REDIRECT_TO_POSTS;
    }

    @GetMapping(PostsRequestUri.DELETE)
    public String showDeletePage(Model model, @PathVariable Long id) {
        iPostsService.remove(id);
        /*TODO: remove xong trong CSDL -
           > delete ca anh co trong storage/images*/
        return PostsViewUri.REDIRECT_TO_POSTS;
    }

    private static String getFileName(PostsDTO postsDTO) {
        MultipartFile multipartFile = postsDTO.getImageFile();
        String fileName = multipartFile.getOriginalFilename();
        return fileName;
    }

    private static boolean isBindingError(BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

    private static void checkUploadImageInvalid(PostsDTO postsDTO, BindingResult bindingResult) {
        if (postsDTO.getImageFile().isEmpty())
            bindingResult.addError(new FieldError("postsDTO", "imageFile", IMAGE_FILE_INVALID_MESSAGE));
    }

    public void savePost(PostsDTO postsDTO, String fileName) {
        Posts posts = getPosts(postsDTO, fileName);
        iPostsService.save(posts);
    }

    public static Posts getPosts(PostsDTO postsDTO, String fileName) {
        Posts posts = new Posts();
        posts.setContent(postsDTO.getContent());
        posts.setTitle(postsDTO.getTitle());
        posts.setShortDescription(postsDTO.getShortDescription());
        posts.setImageFileName(fileName);
        return posts;
    }


}
