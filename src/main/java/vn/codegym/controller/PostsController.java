package vn.codegym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.codegym.model.category.Category;
import vn.codegym.model.posts.Posts;
import vn.codegym.model.posts.PostsDTO;
import vn.codegym.service.ICategoryService;
import vn.codegym.service.IPostsService;
import vn.codegym.uri.PostsRequestUri;
import vn.codegym.uri.PostsViewUri;

import javax.validation.Valid;
import java.util.Optional;


@RequestMapping(PostsRequestUri.POSTS)
@Controller
public class PostsController {

    public static final String IMAGE_FILE_INVALID_MESSAGE = "can them anh vao";
    public static final int PAGE_NUMBER_TO_PRESENT = 5;
    @Autowired
    private IPostsService iPostsService;
    @Autowired
    private ICategoryService iCategoryService;

    @ModelAttribute("categories")
    public Iterable<Category> listCategory() {
        return iCategoryService.findAll();
    }


    @GetMapping({PostsRequestUri.BLANK, PostsRequestUri.SLASH})
    public ModelAndView showPosts(ModelAndView modelAndView, @PageableDefault(value = PAGE_NUMBER_TO_PRESENT) Pageable pageable) {
        Page<Posts> posts = iPostsService.findAll(pageable);
        modelAndView.addObject("posts", posts);
        modelAndView.setViewName(PostsViewUri.POSTS_INDEX);
        return modelAndView;
    }

    @GetMapping(PostsRequestUri.CREATE)
    public ModelAndView showCreateForm(ModelAndView modelAndView, Model model) {
        PostsDTO postsDTO = new PostsDTO();
        modelAndView.addObject("postsDTO", postsDTO);
        modelAndView.setViewName(PostsViewUri.POSTS_CREATE);
        return modelAndView;
    }

    @PostMapping(PostsRequestUri.CREATE)
    public String createPost(@Valid @ModelAttribute("postsDTO") PostsDTO postsDTO, BindingResult bindingResult) {
        iPostsService.checkUploadImageInvalid(postsDTO, bindingResult);
        if (isBindingError(bindingResult)) return PostsViewUri.POSTS_CREATE;
        String fileName = iPostsService.getFileName(postsDTO);
        try {
            iPostsService.saveAnCopyOfFileToStorage(postsDTO, fileName);
            iPostsService.savePost(postsDTO, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PostsViewUri.REDIRECT_TO_POSTS;
    }


    @GetMapping("/{id}/edit")
    public String showEditPage(Model model, @PathVariable Long id) {
        Optional<Posts> posts = iPostsService.findById(id);
        model.addAttribute("posts", posts.get());
        PostsDTO postsDTO = new PostsDTO();
        postsDTO.setTitle(posts.get().getTitle());
        postsDTO.setContent(posts.get().getContent());
        postsDTO.setShortDescription(posts.get().getShortDescription());
        postsDTO.setCategory(posts.get().getCategory());
        model.addAttribute("postsDTO", postsDTO);
        return PostsViewUri.POSTS_EDIT;
    }

    private static PostsDTO getPostsDTO(Posts posts) {
        PostsDTO postsDTO = new PostsDTO();
        postsDTO.setTitle(posts.getTitle());
        postsDTO.setContent(posts.getContent());
        postsDTO.setShortDescription(posts.getShortDescription());
        postsDTO.setCategory(posts.getCategory());

        return postsDTO;
    }

    @PostMapping("/{id}/edit")
    public String editPost(@Valid @ModelAttribute PostsDTO postsDTO, BindingResult bindingResult, @PathVariable Long id) {
        /*TODO:
         *   1. Phai kiem tra xem hinh anh da ton tai trong CSDL chua?
         *   2. edit hinh anh thi phai xoa anh cu
         *   3. luu anh moi vao storage/images*/

        iPostsService.checkUploadImageInvalid(postsDTO, bindingResult);
        if (isBindingError(bindingResult)) return PostsViewUri.POSTS_EDIT;
        Posts posts = iPostsService.findById(id).get();
        posts.setTitle(postsDTO.getTitle());
        posts.setContent(postsDTO.getContent());
        posts.setShortDescription(postsDTO.getShortDescription());
        iPostsService.save(posts);
        return PostsViewUri.REDIRECT_TO_POSTS;
    }

    @GetMapping(PostsRequestUri.DELETE)
    public String showDeletePage(@PathVariable Long id) {
        iPostsService.remove(id);
        /*TODO: remove xong trong CSDL -
           > delete ca anh co trong storage/images*/
        return PostsViewUri.REDIRECT_TO_POSTS;
    }

    @GetMapping("/search")
    public ModelAndView listPosts(@RequestParam("search") Optional<String> search, @PageableDefault(value = PAGE_NUMBER_TO_PRESENT) Pageable pageable) {
        Page<Posts> posts;
        if (search.isPresent()) {
            posts = iPostsService.findAllByTitle(pageable, search.get());
        } else {
            posts = iPostsService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/posts/index");
        modelAndView.addObject("posts", posts);
        return modelAndView;
    }


    private static boolean isBindingError(BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

}
