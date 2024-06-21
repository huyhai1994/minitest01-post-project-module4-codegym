package vn.codegym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.codegym.model.Posts;
import vn.codegym.service.IPostsService;

import java.util.List;


@RequestMapping("/posts")
@Controller
public class PostsController {
    @Autowired
    private IPostsService iPostsService;

    @RequestMapping("/index")
    public ModelAndView index(ModelAndView modelAndView) {

        modelAndView = new ModelAndView();
        List<Posts> posts = iPostsService.findAll();
        /*TODO:
         *   Sua lai cau truy van trong tang repositoty [21/06/2024]
         * */
        modelAndView.addObject("posts", posts);
        modelAndView.setViewName("posts/index");
        return modelAndView;
    }

}
