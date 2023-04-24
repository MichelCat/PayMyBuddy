package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;

/**
 * CustomerAccountDao is the interface that manages CustomerAccountEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public interface CustomerAccountDao extends JpaRepository<CustomerAccountEntity, Integer> {

}
