package com.paymybuddy.paymybuddy.controller;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * SettingsController is the Endpoint of the settings page
 * 
 * @author MC
 * @version 1.0
 */
@Controller
public class SettingsController {
  
  /**
   * Read - Get user settings page attributes
   * 
   * @param principal Currently logged in user
   * @param model Model object
   * @return View 
   */
  @GetMapping("/admin/settings")
  public String getSettings(Principal principal
                          , Model model) {
    return "/admin/settings";
  }
}
