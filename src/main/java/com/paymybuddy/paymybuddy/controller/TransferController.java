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
import com.paymybuddy.paymybuddy.business.TransferBussiness;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.model.Transaction;
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
  private TransferBussiness transferBussiness;
  
  /**
   * Read - Get Transfer Page Attributes
   * 
   * @param page Current page
   * @param size Page size
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * @return View 
   */
  @GetMapping("/transfer")
  public String getTransfer(@RequestParam("page") Optional<Integer> page 
                            , @RequestParam("size") Optional<Integer> size      
                            , Model model
                            , RedirectAttributes redirectAttributes) {
    model.addAttribute("module", "transfer");
    
    int idUser=1;
    
    // Searching the User's Paginated Transaction List
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(3);
    Page<Transaction> transactionPage = transferBussiness.getTransactionsById(idUser, currentPage, pageSize);
    model.addAttribute("transactionPage", transactionPage);
    
    // Search user's buddy list
    List<Buddy> buddies = transferBussiness.getBuddiesById(idUser);
    model.addAttribute("buddies", buddies);
    
    // New transaction record
    Transaction transaction = new Transaction();
    transaction.setIdDebit(idUser);
    model.addAttribute("newTransaction", transaction);

    // New buddy record
    Buddy buddy = new Buddy();
    buddy.setIdUser(idUser);
    model.addAttribute("newBuddy", buddy);
    
    return "transfer";
  }
  

  /**
   * Create - Add a new transaction
   * 
   * @param transaction New transaction record
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @PostMapping("/send-money")
  public String postPayment(@ModelAttribute Transaction transaction
                            , Model model
                            , RedirectAttributes redirectAttributes) {
    try {
      // Adding the new transaction
      transaction = transferBussiness.addTransaction(transaction);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/transfer";
  }


  /**
   * Create - Add a new connection
   * 
   * @param buddy New buddy record
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @PostMapping("/add-connection")
  public String postAddConnection(@ModelAttribute Buddy buddy
                                  , Model model
                                  , RedirectAttributes redirectAttributes) {
    try {
      // Adding the new connection
      buddy = transferBussiness.addBuddy(buddy);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/transfer";
  }
}
