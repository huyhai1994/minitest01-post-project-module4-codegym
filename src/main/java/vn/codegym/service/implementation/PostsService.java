package vn.codegym.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.codegym.model.Posts;
import vn.codegym.repository.IPostsRepository;
import vn.codegym.service.IPostsService;

import java.util.List;

@Service
public class PostsService implements IPostsService {

    @Autowired
    private IPostsRepository iPostsRepository;

    @Override
    public List<Posts> findAll() {
        return iPostsRepository.findAll();
    }

    @Override
    public void save(Posts posts) {
        iPostsRepository.save(posts);
    }

    @Override
    public Posts findById(Long id) {
        return iPostsRepository.findById(id).get();
    }

    @Override
    public void remove(Long id) {
        iPostsRepository.deleteById(id);
        ;
    }
}
