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
import com.paymybuddy.paymybuddy.business.BuddyBussiness;
import com.paymybuddy.paymybuddy.business.TransactionBussiness;
import com.paymybuddy.paymybuddy.controller.model.Buddy;
import com.paymybuddy.paymybuddy.controller.model.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TransactionController {

  @Autowired
  private TransactionBussiness transactionBussiness;
  @Autowired
  private BuddyBussiness buddyBussiness;
  
  @GetMapping("/transactions")
  public String getTransactions(
                            @RequestParam("page") Optional<Integer> page 
                            , @RequestParam("size") Optional<Integer> size      
                            , Model model
                            , RedirectAttributes redirectAttributes) {
    model.addAttribute("module", "transactions");
    
    int idUser=1;
    
    // Pagination
    int currentPage = page.orElse(1);
    int pageSize = size.orElse(3);
    Page<Transaction> transactionPage = transactionBussiness.getTransactionsById(idUser, currentPage, pageSize);
    model.addAttribute("transactionPage", transactionPage);
    
    List<Buddy> buddies = buddyBussiness.getBuddiesById(idUser);
    model.addAttribute("buddies", buddies);
    
    Transaction transaction = new Transaction();
    transaction.setIdDebit(idUser);
    model.addAttribute("newTransaction", transaction);

    Buddy buddy = new Buddy();
    buddy.setIdUser(idUser);
    model.addAttribute("newBuddy", buddy);
    
    return "transactions";
  }
  

  @PostMapping("/send-money")
  public String postPayment(@ModelAttribute Transaction transaction, Model model, RedirectAttributes redirectAttributes) {
    try {
      transaction = transactionBussiness.addTransaction(transaction);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/transactions";
  }


  @PostMapping("/add-connection")
  public String postAddConnection(@ModelAttribute Buddy buddy, Model model) {
    buddy = buddyBussiness.addBuddy(buddy);
    return "redirect:/transactions";
  }
  
  
}
