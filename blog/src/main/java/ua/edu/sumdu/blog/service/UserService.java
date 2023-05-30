/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.edu.sumdu.blog.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.sumdu.blog.dao.PasswordResetTokenDao;
import ua.edu.sumdu.blog.dao.UserDao;
import ua.edu.sumdu.blog.model.PasswordResetToken;
import ua.edu.sumdu.blog.model.User;
import ua.edu.sumdu.blog.util.PasswordEncoder;

/**
 *
 * @author dedg
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordResetTokenDao passwordResetTokenDao;

    @Transactional
    public User register(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userDao.addUser(user);
        return user;
    }
    
    @Transactional
    public boolean authenticate(User user, String password) {
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("remember-me")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }
    
    @Transactional
    public boolean isUsernameTaken(String username) {
        User existingUser = userDao.getUserByUsername(username);
        return existingUser != null;
    }
    
    @Transactional
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenDao.getPasswordResetToken(token);
    }
    
    @Transactional
    public void setPasswordResetToken(PasswordResetToken token) {
        passwordResetTokenDao.setPasswordResetToken(token);
    }
    
    @Transactional
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
    
    @Transactional
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }
    
    @Transactional
    public void update(User user) {
        userDao.update(user);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setPasswordResetTokenDao(PasswordResetTokenDao passwordResetTokenDao) {
        this.passwordResetTokenDao = passwordResetTokenDao;
    }
}