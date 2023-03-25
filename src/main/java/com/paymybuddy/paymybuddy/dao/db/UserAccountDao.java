package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.UserAccountEntity;

/**
 * UserAccountDao is the interface that manages UserAccountEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public interface UserAccountDao extends JpaRepository<UserAccountEntity, Integer> {

}
