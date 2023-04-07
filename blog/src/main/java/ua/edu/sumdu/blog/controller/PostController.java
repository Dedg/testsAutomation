/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.edu.sumdu.blog.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.edu.sumdu.blog.dao.CommentDao;
import ua.edu.sumdu.blog.model.Comment;
import ua.edu.sumdu.blog.model.Post;
import ua.edu.sumdu.blog.model.User;
import ua.edu.sumdu.blog.service.PostService;

/**
 *
 * @author dedg
 */
@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentDao commentDao;
    
    @GetMapping("/posts/add")
    public String addPost(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("title", "Add new post");
        model.addAttribute("activePage", "add");
        model.addAttribute("currentUser", user);
        return "add_edit_post";
    }
    
    @PostMapping("/posts/add")
    public String handlePostAdd(@RequestParam("title") String title, @RequestParam("description") String description, 
            @RequestParam("body") String body, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");
        Post post = new Post(user, title, description, body);
        postService.saveOrUpdatePost(post);
        
        redirectAttributes.addFlashAttribute("successMessage", "The post has been successfully added!");

        return "redirect:/dashboard";
    }
    
    @GetMapping("/posts/{id}")
    public String showPostDetails(@PathVariable("id") Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Post post = postService.getPostById(id);
        
        model.addAttribute("post", post);
        model.addAttribute("currentUser", user);
        
        return "post_details";
    }
    
    @PostMapping("/posts/{id}/comment")
    public String addComment(@PathVariable("id") Long id, @RequestParam("comment") String comment, Model model, HttpSession session) {
        Post post = postService.getPostById(id);
        User user = (User) session.getAttribute("user");
        Comment newComment = new Comment();
        newComment.setMessage(comment);
        newComment.setPost(post);
        newComment.setAuthor(user);
        post.addComment(newComment);
        commentDao.saveOrUpdateComment(newComment);
        
        return "redirect:/posts/" + id;
    }
    
    @GetMapping("posts/{id}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("id") Long id, @PathVariable("commentId") Long commentId, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        postService.deleteCommentById(commentId);
        
        redirectAttributes.addFlashAttribute("successMessage", "The comment has been successfully deleted!");
        
        return "redirect:/posts/{id}";
    }
    
    @GetMapping("/posts/{id}/edit")
    public String showEditPostForm(@PathVariable("id") Long id, Model model, HttpSession session) {
        Post post = postService.getPostById(id);
        User user = (User) session.getAttribute("user");

        model.addAttribute("currentUser", user);
        model.addAttribute("post", post);
        model.addAttribute("title", "Edit post");
        model.addAttribute("activePage", "edit");
        
        return "add_edit_post";
    }
    
    @PostMapping("/posts/{id}/edit")
    public String editPost(@PathVariable("id") Long id, @RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("body") String body, RedirectAttributes redirectAttributes) {
        Post post = postService.getPostById(id);
        
        post.setTitle(title);
        post.setDescription(description);
        post.setBody(body);
        
        postService.saveOrUpdatePost(post);
        
        redirectAttributes.addFlashAttribute("successMessage", "The post has been successfully updated!");
        
        return "redirect:/dashboard";
    }
    
    @GetMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        postService.deletePostById(id);
        
        redirectAttributes.addFlashAttribute("successMessage", "The post has been successfully deleted!");
        
        return "redirect:/dashboard";
    }
}
