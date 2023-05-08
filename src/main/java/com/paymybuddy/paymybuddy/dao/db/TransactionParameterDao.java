package com.paymybuddy.paymybuddy.dao.db;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.paymybuddy.paymybuddy.dao.db.entities.TransactionParameterEntity;

/**
 * TransactionParameterDao is the interface that manages TransactionParameterEntity
 * 
 * @author MC
 * @version 1.0
 */
@Repository
public interface TransactionParameterDao extends JpaRepository<TransactionParameterEntity, Integer> {

  public TransactionParameterEntity findFirstByOrderByEffectiveDateDesc();
  public List<TransactionParameterEntity> findAllByOrderByEffectiveDateDesc();
}
