package com.paymybuddy.paymybuddy.controller.model;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;

public class AppUserPrincipal implements UserDetails {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  private final AppUserEntity user;
  
  
  public AppUserPrincipal(AppUserEntity user) {
    this.user = user;
  }
  
  @Override
  public String getUsername() {
    return user.getUsername();
  }
  
  @Override
  public String getPassword() {
    return user.getPassword();
  }
  
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getAuthorities();
  }
  
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  @Override
  public boolean isEnabled() {
    return true;
  }
  
  public AppUserEntity getAppUser() {
    return user;
  }
}
