package com.paymybuddy.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.paymybuddy.paymybuddy.business.EmailActivationBusiness;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;

/**
 * EmailActivationController is the Endpoint of for email activation pages
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class EmailActivationController {

  @Autowired
  private EmailActivationBusiness emailActivationBusiness;

  /**
   * Update - account activation
   * 
   * @param validationKey Validation key
   * @param model Model object
   * @param redirectAttributes RedirectAttributes object
   * 
   * @return View 
   */
  @GetMapping("/register/{key}")
  public String patchAccountActivation(@PathVariable("key") final String validationKey
                                      , Model model
                                      , RedirectAttributes redirectAttributes) {
    try {
      // Account activation
      AppUserEntity appUserEntity = emailActivationBusiness.emailActivationBusiness(validationKey);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }
    return "redirect:/login";
  }
}
