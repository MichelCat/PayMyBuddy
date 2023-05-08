package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.paymybuddy.paymybuddy.controller.model.User;

/**
 * UserController is the Endpoint of for user pages
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class UserController {

  
  /**
   * Read - Get Login Page Attributes
   * 
   * @param model Model object
   * @return View 
   */
  @GetMapping("/login")
  public String getLogin(Model model) {
    model.addAttribute("module", "login");
    
    // New user record
    User user = new User();
    model.addAttribute("user", user);
    return "login";
  }
  

  /**
   * Create - Login with user
   * 
   * @param user User record
   * @param model Model object
   * 
   * @return View 
   */
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
