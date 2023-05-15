package com.paymybuddy.paymybuddy.dao.db;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.dao.user.entities.PaymybuddyAppUser;

/**
 * CustomerDao is the interface that manages CustomerEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface CustomerDao extends JpaRepository<CustomerEntity, Integer> {

  Optional<CustomerEntity> findByPaymybuddyAppUser(Optional<PaymybuddyAppUser> paymybuddyAppUser);
}
