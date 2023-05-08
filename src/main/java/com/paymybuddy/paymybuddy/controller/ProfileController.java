package com.paymybuddy.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.paymybuddy.paymybuddy.business.ProfileBussiness;
import com.paymybuddy.paymybuddy.controller.model.BankAccount;
import com.paymybuddy.paymybuddy.controller.model.User;

/**
 * ProfileController is the Endpoint of the profile page
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class ProfileController {
  
  @Autowired
  private ProfileBussiness profileBussiness;
  
  /**
   * Read - Get Profile Page Attributes
   * 
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * @return View 
   */
  @GetMapping("/profile")
  public String getProfile(Model model, RedirectAttributes redirectAttributes) {
    model.addAttribute("module", "profile");
    
    int idUser=1;
    
    // Find bank account information
    BankAccount bankAccount = profileBussiness.getBankAccountById(idUser);
    model.addAttribute("bankAccount", bankAccount);

    // Find customer information
    User user = profileBussiness.getUserById(idUser);
    model.addAttribute("user", user);
    
    return "profile";
  }


  /**
   * Update - Update an existing user
   * 
   * @param user The user object updated
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @PutMapping("/profile-identity")
  public String putProfileIdentity(@ModelAttribute User user
                                  , Model model
                                  , RedirectAttributes redirectAttributes) {
    try {
      // Editing customer information
      user = profileBussiness.updateUser(user);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("identityErrorMessage", e.getMessage());
    }
    return "redirect:/profile";
  }


  /**
   * Update - Update an existing bank account
   * 
   * @param bankAccount The bank account object updated
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @PutMapping("/profile-bank-account")
  public String putProfileBankAccount(@ModelAttribute BankAccount bankAccount
                                      , Model model
                                      , RedirectAttributes redirectAttributes) {
    try {
      // Editing bank account information
      bankAccount = profileBussiness.updateBankAccount(bankAccount);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("bankAccountErrorMessage", e.getMessage());
    }
    return "redirect:/profile";
  }

}
