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
  
//  @Modifying
//  @Query(value = "update app_user set"
//      + " user_email = :email"
//      + " , user_expired = :expired"
//      + " , user_locked = :locked"
//      + " , user_credentia_expired = :credentia_expired"
//      + " , user_enabled = :enabled"
//      + " where id_user = :id"
//  , nativeQuery = true)
//  int updateUserSetEmailAndStatus(@Param("email") String email
//                                  , @Param("expired") Boolean expired
//                                  , @Param("locked") Boolean locked
//                                  , @Param("credentia_expired") Boolean credentia_expired
//                                  , @Param("enabled") Boolean enabled
//                                  , @Param("id") long id);
  
}
