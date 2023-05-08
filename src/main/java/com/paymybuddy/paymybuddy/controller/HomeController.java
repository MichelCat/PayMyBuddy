package com.paymybuddy.paymybuddy.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.paymybuddy.paymybuddy.business.HomeBussiness;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.model.CustomerAccount;
import com.paymybuddy.paymybuddy.controller.model.TransactionParameter;
import com.paymybuddy.paymybuddy.controller.model.Transaction;

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
  
  /**
   * Read - Get Home Page Attributes
   * 
   * @param model Model object
   * @return View 
   */
  @GetMapping("/home")
  public String getHome(Model model) {
    model.addAttribute("module", "home");
    
    int idUser=1;
    
    // Last transaction
    Transaction transaction = homeBussiness.getLastTransactionById(idUser);
    model.addAttribute("transaction", transaction);
    
    // Find customer account information
    CustomerAccount customerAccount = homeBussiness.getCustomerAccountById(idUser);
    model.addAttribute("customerAccount", customerAccount);
    
    // Find the list of transaction parameters
    List<TransactionParameter> transactionParameters = homeBussiness.getTransactionParameters();
    model.addAttribute("transactionParameters", transactionParameters);
    
    // Search user's buddy list
    List<Buddy> buddies = homeBussiness.getBuddiesById(idUser);
    model.addAttribute("buddies", buddies);
    
    return "home";
  }
}
