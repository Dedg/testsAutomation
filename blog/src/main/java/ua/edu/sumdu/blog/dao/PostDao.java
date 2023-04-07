/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.edu.sumdu.blog.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.sumdu.blog.model.Post;

/**
 *
 * @author dedg
 */
@Repository("PostDao")
@EnableTransactionManagement
public class PostDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Post> getPosts(int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        Query<Post> query = session.createQuery("from Post order by createdAt desc", Post.class);
        query.setFirstResult((page - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Transactional
    public int getPostCount() {
        Session session = sessionFactory.getCurrentSession();
        Long count = session.createQuery("select count(*) from Post", Long.class).uniqueResult();
        return count.intValue();
    }

    @Transactional
    public Post getPostById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Post.class, id);
    }

    @Transactional
    public void savePost(Post post) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(post);
    }

    @Transactional
    public void deletePostById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Post post = session.get(Post.class, id);
        if (post != null) {
            session.delete(post);
        }
    }
}
