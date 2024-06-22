package vn.codegym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        if (postsDTO.getImageFile().isEmpty())
            bindingResult.addError(new FieldError("postsDTO", "imageFile", "can them anh vao"));
        if (bindingResult.hasErrors())
            return "/posts/create";
        MultipartFile multipartFile = postsDTO.getImageFile();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(postsDTO.getImageFile().getBytes(), new File(fileUpload + fileName));
            Posts posts = new Posts();
            posts.setContent(postsDTO.getContent());
            posts.setTitle(postsDTO.getTitle());
            posts.setShortDescription(postsDTO.getShortDescription());
            posts.setImageFileName(fileName);
            iPostsService.save(posts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/posts";
    }

}
