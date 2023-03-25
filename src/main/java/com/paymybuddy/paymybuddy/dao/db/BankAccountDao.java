package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.BankAccountEntity;

/**
 * BankAccountDao is the interface that manages BankAccountEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public interface BankAccountDao extends JpaRepository<BankAccountEntity, Integer> {

}
