package com.paymybuddy.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.paymybuddy.paymybuddy.business.TransferBusiness;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.model.BankTransaction;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * TransferController is the Endpoint of the transfer page
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class TransferController {

  @Autowired
  private TransferBusiness transferBusiness;
  
  /**
   * Read - Get Transfer Page Attributes
   * 
   * @param page Current page
   * @param size Page size
   * @param principal Currently logged in user
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * @return View 
   */
  @GetMapping("/user/transfer")
  public String getTransfer(@RequestParam("page") Optional<Integer> pageNumber
                            , @RequestParam("size") Optional<Integer> size
                            , Principal principal
                            , Model model
                            , RedirectAttributes redirectAttributes) {
    try {
      Integer idCustomer = transferBusiness.getCustomerId(principal.getName());
      
      // Searching the User's Paginated Transaction List
      int currentPage = pageNumber.orElse(1);
      int pageSize = size.orElse(3);
      Page<BankTransaction> transactionPage = transferBusiness.getTransactionsById(idCustomer, currentPage, pageSize);
      model.addAttribute("transactionPage", transactionPage);
      
      // Search user's buddy list
      List<Buddy> buddies = transferBusiness.getBuddiesById(idCustomer);
      model.addAttribute("buddies", buddies);
      
      // New BankTransaction record
      BankTransaction bankTransaction = new BankTransaction();
      model.addAttribute("newBankTransaction", bankTransaction);

      // New buddy record
      Buddy buddy = new Buddy();
      model.addAttribute("newBuddy", buddy);
      
    } catch (Exception e) {
      model.addAttribute("errorMessage", e.getMessage());
    }
    return "/user/transfer";
  }
  

  /**
   * Create - Add a new BankTransaction
   * 
   * @param bankTransaction New transaction record
   * @param principal Currently logged in user
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @PostMapping("/user/send-money")
  public String postPayment(@ModelAttribute BankTransaction bankTransaction
                            , Principal principal
                            , Model model
                            , RedirectAttributes redirectAttributes) {
    try {
      // Adding the new BankTransaction
      transferBusiness.addTransaction(bankTransaction, principal.getName());
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/user/transfer";
  }


  /**
   * Create - Add a new connection
   * 
   * @param buddy New buddy record
   * @param principal Currently logged in user
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @PostMapping("/user/add-connection")
  public String postAddConnection(@ModelAttribute Buddy buddy
                                  , Principal principal
                                  , Model model
                                  , RedirectAttributes redirectAttributes) {
    try {
      // Adding the new connection
      transferBusiness.addBuddy(buddy, principal.getName());
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/user/transfer";
  }
}
