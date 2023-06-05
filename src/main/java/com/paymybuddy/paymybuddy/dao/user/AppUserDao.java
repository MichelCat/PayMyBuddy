package com.paymybuddy.paymybuddy.dao.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;

public interface AppUserDao extends JpaRepository<AppUserEntity, Long> {

  Optional<AppUserEntity> findByUsername(String username);
  
  Optional<AppUserEntity> findByEmailValidationKey(String validationKey);
  
  @Query(value = "select app_user.*"
                + " from transaction_parameter"
                + " inner join app_user on user_email = contact_email"
                + " order by effective_date desc"
                + " limit 1"
  , nativeQuery = true)
  Optional<AppUserEntity> findByContactEmail();
  
}
