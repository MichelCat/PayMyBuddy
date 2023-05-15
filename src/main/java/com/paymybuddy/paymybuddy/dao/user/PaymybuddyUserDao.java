package com.paymybuddy.paymybuddy.dao.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.user.entities.PaymybuddyAppUser;

public interface PaymybuddyUserDao extends JpaRepository<PaymybuddyAppUser, Long> {

  Optional<PaymybuddyAppUser> findByUsername(String username);
  
//  Optional<PaymybuddyAppUser> findByEmail(String email);
}
