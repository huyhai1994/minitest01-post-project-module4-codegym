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

import javax.validation.Valid;
import java.io.File;
import java.util.List;


@RequestMapping("/posts")
@Controller
public class PostsController {

    @Autowired
    private IPostsService iPostsService;

    @Value("${file-upload}")
    private String fileUpload;

    @GetMapping({"", "/"})
    public ModelAndView index(ModelAndView modelAndView) {
        List<Posts> posts = iPostsService.findAll(Sort.by(Sort.Direction.DESC, "id"));
        modelAndView.addObject("posts", posts);
        modelAndView.setViewName("/posts/index");
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm(ModelAndView modelAndView) {
        PostsDTO postsDTO = new PostsDTO();
        modelAndView.addObject("postsDTO", postsDTO);
        modelAndView.setViewName("/posts/create");
        return modelAndView;
    }

    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute PostsDTO postsDTO, BindingResult bindingResult) {
        /*TODO: @Valid, BindingResult -> check validation of fields*/
        checkUploadImageInvalid(postsDTO, bindingResult);
        if (isBindingError(bindingResult)) return "/posts/create";
        String fileName = getFileName(postsDTO);
        try {
            FileCopyUtils.copy(postsDTO.getImageFile().getBytes(), new File(fileUpload + fileName));
            savePost(postsDTO, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String showEditPage(Model model, @PathVariable Long id) {
        Posts posts = iPostsService.findById(id);
        model.addAttribute("posts", posts);
        PostsDTO postsDTO = new PostsDTO();
        postsDTO.setTitle(posts.getTitle());
        postsDTO.setContent(posts.getContent());
        postsDTO.setShortDescription(posts.getShortDescription());
        model.addAttribute("postsDTO", postsDTO);

        return "/posts/edit";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@Valid @ModelAttribute PostsDTO postsDTO, BindingResult bindingResult, @PathVariable Long id) {
        checkUploadImageInvalid(postsDTO, bindingResult);
        if (isBindingError(bindingResult)) return "/posts/edit";
        Posts posts = iPostsService.findById(id);
        posts.setTitle(postsDTO.getTitle());
        posts.setContent(postsDTO.getContent());
        posts.setShortDescription(postsDTO.getShortDescription());
        iPostsService.save(posts);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/delete")
    public String showDeletePage(Model model, @PathVariable Long id) {
        iPostsService.remove(id);
        return "redirect:/posts";
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
            bindingResult.addError(new FieldError("postsDTO", "imageFile", "can them anh vao"));
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
