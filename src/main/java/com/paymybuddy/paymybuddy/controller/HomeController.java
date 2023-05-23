package com.paymybuddy.paymybuddy.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.paymybuddy.paymybuddy.business.HomeBussiness;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.model.CustomerAccount;
import com.paymybuddy.paymybuddy.controller.model.TransactionParameter;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserRole;
import com.paymybuddy.paymybuddy.controller.model.BankTransaction;

/**
 * HomeController is the Endpoint of the home page
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class HomeController {
  
  @Autowired
  private HomeBussiness homeBussiness;
  
  
  @GetMapping("/default")
  public String getDefaultUser() {
    Collection<? extends GrantedAuthority> authorities;
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();    
    authorities = auth.getAuthorities();
    String myRole = authorities.toArray()[0].toString();
    if (myRole.equals(AppUserRole.ADMIN_ROLE.name())) {
      return "redirect:/admin/home";
    }    
    return "redirect:/user/home";
  }
  
  /**
   * Read - Get user home page attributes
   * 
   * @param principal Currently logged in user
   * @param model Model object
   * @return View 
   */
  @GetMapping("/user/home")
//  @PreAuthorize("hasAuthority('USER_ROLE')")
  public String getUserHome(Principal principal
                          , Model model) {
    try {
      Integer idCustomer = homeBussiness.getCustomerId(principal.getName());
      
      // Last transaction
      List<BankTransaction> bankTransactions = homeBussiness.getLastTransactionById(idCustomer);
      model.addAttribute("bankTransactions", bankTransactions);
      
      // Find customer account information
      CustomerAccount customerAccount = homeBussiness.getCustomerAccountById(idCustomer);
      model.addAttribute("customerAccount", customerAccount);
      
      // Find the list of transaction parameters
      List<TransactionParameter> transactionParameters = homeBussiness.getTransactionParameters();
      model.addAttribute("transactionParameters", transactionParameters);
      
      // Search user's buddy list
      List<Buddy> buddies = homeBussiness.getBuddiesById(idCustomer);
      model.addAttribute("buddies", buddies);
      
    } catch (Exception e) {
      model.addAttribute("errorMessage", e.getMessage());
    }
    return "/user/home";
  }
  
  /**
   * Read - Get admin home page attributes
   * 
   * @param principal Currently logged in user
   * @param model Model object
   * @return View 
   */
  @GetMapping("/admin/home")
//  @PreAuthorize("hasAuthority('ADMIN_ROLE')")
  public String getAdminHome(Principal principal
                            , Model model) {
    return "/admin/home";
  }
}
