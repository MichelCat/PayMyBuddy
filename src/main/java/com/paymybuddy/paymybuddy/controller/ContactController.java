package com.paymybuddy.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.paymybuddy.paymybuddy.business.ContactBussiness;
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
  private ContactBussiness contactBussiness;

  /**
   * Read - Get Contact Page Attributes
   * 
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * @return View 
   */
  @GetMapping("/contact")
  public String getContact(Model model
                      , RedirectAttributes redirectAttributes) {
    model.addAttribute("module", "contact");
    
    int idUser=1;
    
    // New customer message record
    CustomerMessage customerMessage = new CustomerMessage();
    customerMessage.setIdCustomer(idUser);
    model.addAttribute("newCustomerMessage", customerMessage);
    
    return "contact";
  }

  /**
   * Create - Add a new customer message
   * 
   * @param customerMessage New customer message record
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @PostMapping("/contact")
  public String postCustomerMessage(@ModelAttribute CustomerMessage customerMessage
                            , Model model
                            , RedirectAttributes redirectAttributes) {
    try {
      // Adding the new transaction
      customerMessage = contactBussiness.addCustomerMessage(customerMessage);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/contact";
  }
}
