package vn.codegym.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping({"", "/"})
    public ModelAndView index(ModelAndView modelAndView) {
        List<Posts> posts = iPostsService.findAll();
        System.out.println("index");
        modelAndView.addObject("posts", posts);
        modelAndView.setViewName("/posts/index");
        return modelAndView;
    }

}
