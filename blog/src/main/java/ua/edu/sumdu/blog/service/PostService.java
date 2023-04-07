/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.edu.sumdu.blog.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.blog.dao.CommentDao;
import ua.edu.sumdu.blog.model.Comment;
import ua.edu.sumdu.blog.dao.PostDao;
import ua.edu.sumdu.blog.model.Post;

/**
 *
 * @author dedg
 */
@Service
public class PostService {
    @Autowired
    private PostDao postDao;
    @Autowired
    private CommentDao commentDao;

    public List<Post> getPosts(int pageNo, int pageSize) {
        return postDao.getPosts(pageNo, pageSize);
    }

    public long getTotalPosts() {
        return postDao.getPostCount();
    }

    public Post getPostById(Long id) {
        return postDao.getPostById(id);
    }

    public void saveOrUpdatePost(Post post) {
        postDao.savePost(post);
    }

    public void deletePostById(Long id) {
        postDao.deletePostById(id);
    }

    public List<Comment> getCommentsByPostId(Long id) {
        return commentDao.getCommentsByPostId(id);
    }

    public void saveOrUpdateComment(Comment comment) {
        commentDao.saveOrUpdateComment(comment);
    }

    public void deleteCommentById(Long id) {
        commentDao.deleteCommentById(id);
    }
}
