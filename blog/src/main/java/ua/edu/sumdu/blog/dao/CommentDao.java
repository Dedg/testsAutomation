/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.edu.sumdu.blog.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.sumdu.blog.model.Comment;

/**
 *
 * @author dedg
 */
@Repository("CommentDao")
@EnableTransactionManagement
public class CommentDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public List<Comment> getCommentsByPostId(Long postId) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        Query<Comment> query = session.createQuery("from Comment where post.id = :postId order by createdDate desc", Comment.class);
        query.setParameter("postId", postId);
        return query.getResultList();
    }

    @Transactional
    public void saveOrUpdateComment(Comment comment) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(comment);
    }

    @Transactional
    public void deleteCommentById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Comment comment = session.get(Comment.class, id);
        if (comment != null) {
            session.delete(comment);
        }
    }
}
