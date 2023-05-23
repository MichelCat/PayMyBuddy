package com.paymybuddy.paymybuddy.dao.user.entities;

// User role list

/**
 * AppUserRole is Enumeration for list of user roles
 * 
 * @author MC
 * @version 1.0
 */
public enum AppUserRole {
  USER_ROLE("USER_ROLE")
  , ADMIN_ROLE("ADMIN_ROLE");

  public final String role;

  private AppUserRole(String role) {
    this.role = role;
  }
}
