package com.paymybuddy.paymybuddy.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.paymybuddy.paymybuddy.controller.model.Register;
import com.paymybuddy.paymybuddy.business.AppUserBusiness;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;

/**
 * UserController is the Endpoint of for user pages
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class UserController {

  @Autowired
  private AppUserBusiness appUserBusiness;
  
  /**
   * Read - Get Login Page Attributes
   * 
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * @param params Parameter list
   * @return View 
  */
  @GetMapping("/login")
  public String getLogin(Model model
                        , RedirectAttributes redirectAttributes
                        , @RequestParam Map<String,String> params) {
    if (params.containsKey("logout")) {
      model.addAttribute("logout", true);
    }
    if (params.containsKey("error")) {
      model.addAttribute("error", true);
    }
    // User record exists
    AppUserEntity appUserEntity = new AppUserEntity();
    model.addAttribute("user", appUserEntity);
    return "login";
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
      appUserBusiness.addCustomer(register);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
      return "redirect:/register";
   }
   return "redirect:/login";
  }
  
}
