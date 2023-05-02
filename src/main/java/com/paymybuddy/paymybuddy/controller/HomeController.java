package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  
  @GetMapping("/home")
  public String getHome(Model model) {
    model.addAttribute("module", "home");
    
    return "home";
  }
}
