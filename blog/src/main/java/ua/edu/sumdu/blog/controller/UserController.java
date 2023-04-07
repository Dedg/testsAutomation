/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.edu.sumdu.blog.controller;

import java.util.UUID;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ua.edu.sumdu.blog.dao.UserDao;
import ua.edu.sumdu.blog.form.LoginForm;
import ua.edu.sumdu.blog.form.RegistrationForm;
import ua.edu.sumdu.blog.model.PasswordResetToken;
import ua.edu.sumdu.blog.model.User;
import ua.edu.sumdu.blog.service.EmailService;
import ua.edu.sumdu.blog.service.UserService;
import ua.edu.sumdu.blog.util.PasswordEncoder;

/**
 *
 * @author dedg
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String dashboard(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "dashboard";
    }
    
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registrationForm") RegistrationForm form, BindingResult bindingResult, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        if ( form.getUsername().isEmpty()) {
            model.addAttribute("errorMessage", "The Username is required and cannot be empty");
            return "register";
        }
        
        if ( form.getEmail().isEmpty()) {
            model.addAttribute("errorMessage", "The Email is required and cannot be empty");
            return "register";
        }
        
        if ( form.getPassword().isEmpty()) {
            model.addAttribute("errorMessage", "The Password is required and cannot be empty");
            return "register";
        }
        
        if ( !form.getPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("errorMessage", "The password and its confirm are not the same");
            return "register";
        }
        
        if (this.userService.isUsernameTaken(form.getUsername())) {
            model.addAttribute("errorMessage", "Username is already taken.");
            return "register";
        }

        User user = this.userService.register(form.getUsername(), form.getPassword(), form.getEmail());
        session.setAttribute("user", user);
        redirectAttributes.addFlashAttribute("successMessage", "Congrats! Your registration has been successful.");
        return "redirect:/dashboard";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpSession session, Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }

        if ( form.getUsername().isEmpty()) {
            model.addAttribute("errorMessage", "The Username is required and cannot be empty");
            return "login";
        }
        
        if ( form.getPassword().isEmpty()) {
            model.addAttribute("errorMessage", "The Password is required and cannot be empty");
            return "login";
        }
        
        User user = userService.getUserByUsername(form.getUsername());
                
        if ( ! this.userService.authenticate(user, form.getPassword()) ) {
            model.addAttribute("errorMessage", "Invalid username/password, Try again!");
            return "login";
        }

        session.setAttribute("user", user);
        return "redirect:/dashboard";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/forgot-password")
    public String forgotPassword(HttpSession session) {
        return "forgot_password";
    }
    
    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email, HttpSession session, Model model, HttpServletRequest request) throws MessagingException {
        User user = userService.getUserByEmail(email);

        if (user == null) {
            String errorMessage = "No account with that email address exists";
            model.addAttribute("errorMessage", errorMessage);
            return "forgot_password";
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token);
        passwordResetToken.setUser(user);
        userService.setPasswordResetToken(passwordResetToken);

        String resetLink = "http://" + request.getServerName() + ":" + request.getServerPort() 
                + request.getContextPath() + "/reset-password?token=" + token;



        String emailContent = "Click the following link to reset your password: " + resetLink;
        EmailService emailService = new EmailService();
        if ( emailService.sendEmail(email, "Password Reset", emailContent) ) {
            String successMessage = "An email has been sent to " + email + " with instructions on how to reset your password.";
            request.setAttribute("successMessage", successMessage);
        } else {
            request.setAttribute("errorMessage", "Something went wrong.");
        }
        return "forgot_password";
    }
    
    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam(name = "token", required = false) String token, RedirectAttributes redirectAttributes, HttpSession session) {
        PasswordResetToken resetToken = userService.getPasswordResetToken(token);
        if (resetToken == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Password reset token is invalid");
            return "redirect:/forgot-password";
        }

        if (resetToken.isExpired()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Password reset token has expired.");
            return "redirect:/forgot-password";
        }

        session.setAttribute("user", resetToken.getUser());
        return "reset_password";
    }
    
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String password, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("errorMessage", "Something went wrong");
            return "forgot_password";
        }
        user.setPassword(PasswordEncoder.encode(password));
        UserDao userDao = new UserDao();
        userDao.update(user);
        
        return "login";
    }
    
    @GetMapping("/profile")
    public String viewProfile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        
        model.addAttribute("currentUser", user);
        model.addAttribute("activePage", "profile");

        return "profile";
    }
    
    @PostMapping("/profile")
    public String updateProfile(Model model, HttpSession session, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("user");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        int age = Integer.parseInt(request.getParameter("age"));
        String gender = request.getParameter("gender");
        String website = request.getParameter("website");

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setAge(age);
        user.setGender(gender);
        user.setWebsite(website);
        userService.update(user);

        session.setAttribute("user", user);
        model.addAttribute("user", user);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");

        return "redirect:/dashboard";
    }
}