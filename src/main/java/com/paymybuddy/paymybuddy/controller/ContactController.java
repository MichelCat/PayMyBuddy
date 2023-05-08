package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ContactController is the Endpoint of the contact page
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class ContactController {

  /**
   * Read - Get Contact Page Attributes
   * 
   * @param model Model object
   * @return View 
   */
  @GetMapping("/contact")
  public String getContact(Model model) {
    model.addAttribute("module", "contact");
    
    return "contact";
  }
  
}
