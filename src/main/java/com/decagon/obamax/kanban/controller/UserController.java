package com.decagon.obamax.kanban.controller;


import com.decagon.obamax.kanban.model.User;
import com.decagon.obamax.kanban.payload.UserResponse;
import com.decagon.obamax.kanban.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping()
    public String authenticate(Model model){
        model.addAttribute("register_user", new User());
        return "auth";
    }

    @RequestMapping("/register")
    public String register(@ModelAttribute("register_user") User user,
                           RedirectAttributes redirectAttributes) {
        UserResponse signupResponse = userService.addUser(user);
        redirectAttributes.addFlashAttribute("error", signupResponse.getMessage());
        return "redirect:/auth/";
    }

    @RequestMapping("/login")
    public String login(@ModelAttribute("register_user") User user, HttpSession session,
                        RedirectAttributes redirectAttributes) {
        UserResponse loginResponse = userService.logIn(user);
        if(loginResponse.isSuccessful()) {
            session.setAttribute("currentUser", loginResponse.getUser());
            String message = "Welcome Back " + loginResponse.getUser().getUsername();
            redirectAttributes.addFlashAttribute("message", message);
            return "redirect:/home";
        }
        redirectAttributes.addFlashAttribute("error", loginResponse.getMessage());
        return "redirect:/auth/";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/auth/";
    }

}