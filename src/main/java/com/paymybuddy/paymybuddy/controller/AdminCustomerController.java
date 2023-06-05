package com.paymybuddy.paymybuddy.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.paymybuddy.paymybuddy.business.AdminCustomerBusiness;
import com.paymybuddy.paymybuddy.controller.model.CustomerUser;

/**
 * SettingsController is the Endpoint of the settings page
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class AdminCustomerController {

  @Autowired
  private AdminCustomerBusiness adminCustomerBusiness;
  
  /**
   * Read - Get customer users settings page attributes
   * 
   * @param page Current page
   * @param size Page size
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * @return View 
   */
  @GetMapping("/admin/customer")
  public String getCustomerUsers(@RequestParam("page") Optional<Integer> pageNumber
                          , @RequestParam("size") Optional<Integer> size
                          , Model model
                          , RedirectAttributes redirectAttributes) {
    
    // Searching the User's Paginated customer users List
    int currentPage = pageNumber.orElse(1);
    int pageSize = size.orElse(3);
    Page<CustomerUser> customerUserPage = adminCustomerBusiness.getCustomerUserById(currentPage, pageSize);
    model.addAttribute("customerUserPage", customerUserPage);
    
    CustomerUser newCustomerUser = new CustomerUser();
    model.addAttribute("newCustomerUser", newCustomerUser);
    
    return "/admin/customer";
  }
  
  /**
   * Update - Update an existing customer user
   * 
   * @param customerUser The customer user object updated
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @PatchMapping("/admin/customer/edit-user")
  public String patchCustomerUser(@ModelAttribute CustomerUser customerUser
                                  , Model model
                                  , RedirectAttributes redirectAttributes) {
    try {
      // Editing customer information
      adminCustomerBusiness.updateUser(customerUser);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/admin/customer";
  }
  
  /**
   * Delete - Delete an customer user
   * 
   * @param IdUser - The id of the user to delete
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @DeleteMapping("/admin/customer/delete-user/{id}")
  public String deleteCustomerUser(@PathVariable("id") final Long IdUser
                                  , Model model
                                  , RedirectAttributes redirectAttributes) {
    try {
      adminCustomerBusiness.deleteUser(IdUser);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/admin/customer";
  }  
}
