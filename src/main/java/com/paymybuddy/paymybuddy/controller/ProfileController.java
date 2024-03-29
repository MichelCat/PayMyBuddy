package com.paymybuddy.paymybuddy.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.paymybuddy.paymybuddy.business.ProfileBusiness;
import com.paymybuddy.paymybuddy.controller.model.BankAccount;
import com.paymybuddy.paymybuddy.controller.model.Customer;

/**
 * ProfileController is the Endpoint of the profile page
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class ProfileController {
  
  @Autowired
  private ProfileBusiness profileBusiness;
  
  /**
   * Read - Get Profile Page Attributes
   * 
   * @param principal Currently logged in user
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * @return View 
   */
  @GetMapping("/user/profile")
  public String getProfile(Principal principal
                          , Model model
                          , RedirectAttributes redirectAttributes) {
    try {
      // Find customer information
      Customer customer = profileBusiness.getCustomerByUsername(principal.getName());
      model.addAttribute("user", customer);
      
      // Find bank account information
      BankAccount bankAccount = profileBusiness.getBankAccountById(customer.getId());
      model.addAttribute("bankAccount", bankAccount);
      
    } catch (Exception e) {
      model.addAttribute("errorMessage", e.getMessage());
    }
    return "/user/profile";
  }


  /**
   * Update - Update an existing user
   * 
   * @param customer The user object updated
   * @param principal Currently logged in user
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @PatchMapping("/user/profile-identity")
  public String patchProfileIdentity(@ModelAttribute Customer customer
                                  , Principal principal
                                  , Model model
                                  , RedirectAttributes redirectAttributes) {
    try {
      // Editing customer information
      profileBusiness.updateUser(customer, principal.getName());
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/user/profile";
  }


  /**
   * Create - Add/Update a bank account
   * 
   * @param bankAccount The bank account object updated
   * @param principal Currently logged in user
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @PostMapping("/user/profile-bank-account")
  public String postProfileBankAccount(@ModelAttribute BankAccount bankAccount
                                      , Principal principal
                                      , Model model
                                      , RedirectAttributes redirectAttributes) {
    try {
      // Editing bank account information
      profileBusiness.saveBankAccount(bankAccount, principal.getName());
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/user/profile";
  }

}
