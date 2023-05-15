package com.paymybuddy.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.paymybuddy.paymybuddy.controller.model.Register;
import com.paymybuddy.paymybuddy.business.PaymybuddyAppUserBusiness;
import com.paymybuddy.paymybuddy.dao.user.entities.PaymybuddyAppUser;

/**
 * UserController is the Endpoint of for user pages
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class UserController {

  @Autowired
  private PaymybuddyAppUserBusiness paymybuddyAppUserBusiness;
  
  /**
   * Read - Get Login Page Attributes
   * 
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * @return View 
  */
  @GetMapping("/login")
  public String getLogin(Model model
                        , RedirectAttributes redirectAttributes) {
    model.addAttribute("module", "login");
    
    // User record exists
    PaymybuddyAppUser paymybuddyAppUser = new PaymybuddyAppUser();
    model.addAttribute("user", paymybuddyAppUser);
    return "login";
  }
    
  /**
   * Read - Get Login with error
   * 
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * @return View 
  */
  @GetMapping("/login-error.html")
  public String getLoginError(Model model
                            , RedirectAttributes redirectAttributes) {
    redirectAttributes.addFlashAttribute("errorMessage", "Wrong user or password");
    return "redirect:/login";
  }
  
  
  /**
   * Read - Get Register Page Attributes
   * 
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * @return View 
  */
  @GetMapping("/register")
  public String getRegister(Model model
                        , RedirectAttributes redirectAttributes) {
    model.addAttribute("module", "register");
    
    // New user record
    Register register = new Register();
    model.addAttribute("user", register);
    return "register";
  }
   
  /**
  * Create - Register with user
  * 
  * @param register Register record
  * @param model Model object
  * @param redirectAttributes RedirectAttributes object
  * 
  * @return View 
  */
    @PostMapping("/register")
    public String postRegister(@ModelAttribute Register register
                              , Model model
                              , RedirectAttributes redirectAttributes) {    
   try {
      // Adding the new user
      paymybuddyAppUserBusiness.addUser(register);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      return "redirect:/register";
   }
   return "redirect:/home";
  }
    
  
  /**
   * Read - Get Logout Page Attributes
   * 
   * @param model Model object
   * @return View 
   */
  @GetMapping("/logout")
  public String getLogout(Model model) {
    model.addAttribute("module", "logout");
    return "logout";
  }
  
  
  @GetMapping("/403")
  public String error403() {
    return "/error/403";
  }
}
