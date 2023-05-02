package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.paymybuddy.paymybuddy.controller.model.User;

@Controller
public class UserController {

  
  @GetMapping("/login")
  public String getLogin(Model model) {
    model.addAttribute("module", "login");
    
    User user = new User();
    model.addAttribute("user", user);
    return "login";
  }
  

  @PostMapping("/login")
  public String postLogin(@ModelAttribute User user, Model model) {
    return "redirect:/home";
  }
  
  
  @GetMapping("/logout")
  public String getLogout(Model model) {
    model.addAttribute("module", "logout");
    
    return "logout";
  }
  
}
