package com.paymybuddy.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.paymybuddy.paymybuddy.business.ProfileBussiness;
import com.paymybuddy.paymybuddy.controller.model.BankAccount;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.model.User;

@Controller
public class ProfileController {
  
  @Autowired
  private ProfileBussiness profileBussiness;
  
  @GetMapping("/profile")
  public String getProfile(Model model) {
    model.addAttribute("module", "profile");
    
    int idUser=1;
    
    BankAccount bankAccount = profileBussiness.getBankAccountById(idUser);
    model.addAttribute("bankAccount", bankAccount);

    User user = profileBussiness.getUserById(idUser);
    model.addAttribute("user", user);
    
    return "profile";
  }


  @PostMapping("/profile-identity")
  public String postProfileIdentity(@ModelAttribute User user, Model model) {
    user = profileBussiness.changeUser(user);
    return "redirect:/profile";
  }


  @PostMapping("/profile-bank-account")
  public String postProfileBankAccount(@ModelAttribute BankAccount bankAccount, Model model) {
    bankAccount = profileBussiness.changeBankAccount(bankAccount);
    return "redirect:/profile";
  }

}
