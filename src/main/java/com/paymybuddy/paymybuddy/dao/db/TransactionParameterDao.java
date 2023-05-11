package com.paymybuddy.paymybuddy.dao.db;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.TransactionParameterEntity;

/**
 * TransactionParameterDao is the interface that manages TransactionParameterEntity
 * 
 * @author MC
 * @version 1.0
 */
public interface TransactionParameterDao extends JpaRepository<TransactionParameterEntity, Integer> {

  public Optional<TransactionParameterEntity> findFirstByOrderByEffectiveDateDesc();
  
  public List<TransactionParameterEntity> findAllByOrderByEffectiveDateDesc();
}
