package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerMessageEntity;

/**
 * CustomerMessageDao is the interface that manages CustomerMessageEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface CustomerMessageDao extends JpaRepository<CustomerMessageEntity, Integer> {

}
