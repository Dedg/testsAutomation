/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.edu.sumdu.blog.controller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.edu.sumdu.blog.model.Post;
import ua.edu.sumdu.blog.model.User;
import ua.edu.sumdu.blog.service.PostService;

/**
 *
 * @author dedg
 */
@Controller
public class DashboardController {
    @Autowired
    private PostService postService;
    
    @GetMapping("/dashboard")
    public String showDashboard(@RequestParam(name = "page", defaultValue = "1") int page, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Post> posts = postService.getPosts(page, 15);
        
        model.addAttribute("posts", posts);
        model.addAttribute("currentUser", user);
        model.addAttribute("currentPage", page);
        model.addAttribute("activePage", "dashboard");
        
        return "dashboard";
    }
}
