package com.paymybuddy.paymybuddy.controller;

import java.security.Principal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.paymybuddy.paymybuddy.business.ContactBusiness;
import com.paymybuddy.paymybuddy.controller.model.CustomerMessage;

/**
 * ContactController is the Endpoint of the contact page
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class ContactController {
  
  @Autowired
  private ContactBusiness contactBusiness;

  /**
   * Read - Get Contact Page Attributes
   * 
   * @param page Current page
   * @param size Page size
   * @param principal Currently logged in user
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * @return View 
   */
  @GetMapping("/user/contact")
  public String getContact(@RequestParam("page") Optional<Integer> pageNumber
                          , @RequestParam("size") Optional<Integer> size
                          , Principal principal
                          , Model model
                          , RedirectAttributes redirectAttributes) {
    try {
      // New customer message record
      CustomerMessage customerMessage = new CustomerMessage();
      model.addAttribute("newCustomerMessage", customerMessage);
      
      // List of customer messages
      int currentPage = pageNumber.orElse(1);
      int pageSize = size.orElse(3);
      Page<CustomerMessage> customerMessagePage = contactBusiness.getCustomerMessageById(principal.getName(), currentPage, pageSize);
      model.addAttribute("customerMessagePage", customerMessagePage);
      
    } catch (Exception e) {
      model.addAttribute("errorMessage", e.getMessage());
    }
    return "/user/contact";
  }

  /**
   * Create - Add a new customer message
   * 
   * @param customerMessage New customer message record
   * @param principal Currently logged in user
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @PostMapping("/user/contact")
  public String postCustomerMessage(@ModelAttribute CustomerMessage customerMessage
                                  , Principal principal
                                  , Model model
                                  , RedirectAttributes redirectAttributes) {
    try {
      // Adding the new transaction
      customerMessage = contactBusiness.addCustomerMessage(customerMessage, principal.getName());
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/user/contact";
  }
}
