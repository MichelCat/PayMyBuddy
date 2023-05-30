package com.paymybuddy.paymybuddy.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.paymybuddy.paymybuddy.business.HomeBusiness;
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
  private HomeBusiness homeBusiness;
  
  
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
   * @param page Current page
   * @param size Page size
   * @param principal Currently logged in user
   * @param model Model object
   * @return View 
   */
  @GetMapping("/user/home")
  public String getUserHome(@RequestParam("buddyPage") Optional<Integer> buddyPageNumber 
                          , @RequestParam("buddySize") Optional<Integer> buddySize
                          , @RequestParam("transactionPage") Optional<Integer> transactionPageNumber 
                          , @RequestParam("transactionSize") Optional<Integer> transactionSize
                          , Principal principal
                          , Model model) {
    try {
      Integer idCustomer = homeBusiness.getCustomerId(principal.getName());
      
      // Last transaction
      List<BankTransaction> bankTransactions = homeBusiness.getLastTransactionById(idCustomer);
      model.addAttribute("bankTransactions", bankTransactions);
      
      // Find customer account information
      CustomerAccount customerAccount = homeBusiness.getCustomerAccountById(idCustomer);
      model.addAttribute("customerAccount", customerAccount);
      
      // Find the list of transaction parameters
      int currentTransactionPage = transactionPageNumber.orElse(1);
      int transactionPageSize = transactionSize.orElse(3);
      Page<TransactionParameter> transactionParameterPage = homeBusiness.getTransactionParameters(currentTransactionPage, transactionPageSize);
      model.addAttribute("transactionParameterPage", transactionParameterPage);
      
      // Search user's buddy list
      int currentBuddyPage = buddyPageNumber.orElse(1);
      int buddyPageSize = buddySize.orElse(3);
      Page<Buddy> buddyPage = homeBusiness.getBuddiesById(idCustomer, currentBuddyPage, buddyPageSize);
      model.addAttribute("buddyPage", buddyPage);
      
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
  public String getAdminHome(Principal principal
                            , Model model) {
    return "/admin/home";
  }
}
