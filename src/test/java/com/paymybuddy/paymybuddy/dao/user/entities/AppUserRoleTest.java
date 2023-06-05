package com.paymybuddy.paymybuddy.dao.user.entities;

import static org.junit.Assert.assertSame;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * AppUserRoleTest is the unit test class for enumerating user roles
 * 
 * @author MC
 * @version 1.0
 */
@SpringBootTest
class AppUserRoleTest {
  
  /**
   * Read USER_ROLE
   */
  @Test
  void getUserRole_returnUserRole() {
    // GIVEN
    // WHEN
    assertSame(AppUserRole.USER_ROLE, AppUserRole.valueOf("USER_ROLE"));    
    // THEN
  }  
  
  /**
   * Read ADMIN_ROLE
   */
  @Test
  void getAdminRole_returnAdmin() {
    // GIVEN
    // WHEN
    assertSame(AppUserRole.ADMIN_ROLE, AppUserRole.valueOf("ADMIN_ROLE"));    
    // THEN
  }  
}
