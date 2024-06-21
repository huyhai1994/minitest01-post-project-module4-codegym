package vn.codegym.repository.implementation;

import org.springframework.stereotype.Repository;
import vn.codegym.model.Posts;
import vn.codegym.repository.IPostsRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class PostsRepository implements IPostsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Posts> findAll() {
        TypedQuery<Posts> query = entityManager.createQuery("select p from Posts p left join Category c where p.category.id = c.id", Posts.class);
        return query.getResultList();
    }

    @Override
    public Posts findById(Long id) {
        TypedQuery<Posts> query = entityManager.createQuery("select p, c from Posts p join Category  c on c.id= p.category.id where p.id =:id", Posts.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(Posts posts) {
        if (posts.getId() != null) {
            entityManager.merge(posts);
        }
        entityManager.persist(posts);
    }

    @Override
    public void remove(Long id) {
        Posts posts = findById(id);
        if (posts != null) {
            entityManager.remove(posts);
        }
    }
}
