package vn.codegym.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.codegym.model.DTO.ICountPosts;
import vn.codegym.model.posts.Posts;
import vn.codegym.service.IPostsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin("*")
public class RestPostsController {
    private final IPostsService iPostsService;

    public RestPostsController(IPostsService iPostsService) {
        this.iPostsService = iPostsService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Posts>> findAllPosts() {
        List<Posts> posts = (List<Posts>) iPostsService.findAll();
        /*TODO: su dung order by JPA o reposipotory - sap xep xuat hien tu duoi len*/
        if (posts.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Posts> findPostById(@PathVariable Long id) {
        Optional<Posts> postOptional = iPostsService.findById(id);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postOptional.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Posts> updatePost(@PathVariable Long id, @RequestBody Posts posts) {
        Optional<Posts> postOptional = iPostsService.findById(id);
        if (!postOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        posts.setId(postOptional.get().getId());
        iPostsService.save(posts);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Posts> createPost(@RequestBody Posts posts) {
        iPostsService.save(posts);
        return new ResponseEntity<>(posts, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Posts> deletePost(@PathVariable Long id) {
        Optional<Posts> postsOptional = iPostsService.findById(id);
        if (!postsOptional.isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        iPostsService.remove(id);
        return new ResponseEntity<>(postsOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Iterable<ICountPosts>> getCountPostsByCategories() {
        Iterable<ICountPosts> posts = iPostsService.getCountPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
