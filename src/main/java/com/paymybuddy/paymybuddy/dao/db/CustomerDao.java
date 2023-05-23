package com.paymybuddy.paymybuddy.dao.db;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.user.entities.AppUserEntity;

/**
 * CustomerDao is the interface that manages CustomerEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface CustomerDao extends JpaRepository<CustomerEntity, Integer> {

  Optional<CustomerEntity> findByAppUserEntity(Optional<AppUserEntity> appUserEntity);
  
  @Query(value = "select customer.*"
                + " from app_user"
                + " inner join customer on customer_id_user = id_user"
                + " where user_email = :username"
      , nativeQuery = true)
  Optional<CustomerEntity> findByUsername(@Param("username") String username);
}
