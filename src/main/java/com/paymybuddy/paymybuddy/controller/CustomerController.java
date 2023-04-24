package com.paymybuddy.paymybuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.paymybuddy.paymybuddy.controller.model.Customer;

@Controller
public class CustomerController {

  
  @GetMapping("/login")
  public String getLogin(Model model) {
    Customer customer = new Customer();
    model.addAttribute("customer", customer);
    return "login";
  }
}
