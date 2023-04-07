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
import ua.edu.sumdu.blog.model.PasswordResetToken;
import ua.edu.sumdu.blog.model.Post;

/**
 *
 * @author dedg
 */
@Repository("PasswordResetTokenDao")
@EnableTransactionManagement
public class PasswordResetTokenDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public PasswordResetToken getPasswordResetToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        Query<PasswordResetToken> query = session.createQuery("from PasswordResetToken where token=:token", PasswordResetToken.class);
        query.setParameter("token", token);
        List<PasswordResetToken> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
    
    @Transactional
    public void setPasswordResetToken(PasswordResetToken token) {
        Session session = sessionFactory.getCurrentSession();
        session.save(token);
    }
}
