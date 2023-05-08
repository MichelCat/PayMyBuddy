package com.paymybuddy.paymybuddy.controller.utils;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.paymybuddy.paymybuddy.controller.model.TransactionParameter;
import com.paymybuddy.paymybuddy.dao.db.entities.TransactionParameterEntity;

/**
 * TransactionParameterUtils is an TransactionParameter object conversion utility class
 * 
 * @author MC
 * @version 1.0
 */
@Service
public class TransactionParameterUtils {

  /**
   * Conversion TransactionParameterEntity list to TransactionParameter list
   * 
   * @param transactionParameterEntities TransactionParameterEntity list
   * @return TransactionParameter list
   */
  public List<TransactionParameter> fromListTransactionParameterEntityToListTransactionParameter(List<TransactionParameterEntity> transactionParameterEntities) {
    List<TransactionParameter> transactionParameters = new ArrayList<>();
    transactionParameterEntities.forEach(e -> {
      transactionParameters.add(fromTransactionParameterEntityToTransactionParameter(e));
    });
    return transactionParameters;
  }
  
  /**
   * Conversion TransactionParameterEntity to TransactionParameter
   * 
   * @param transactionParameterEntity TransactionParameterEntity object
   * @return TransactionParameter
   */
  public TransactionParameter fromTransactionParameterEntityToTransactionParameter(TransactionParameterEntity transactionParameterEntity) {
    TransactionParameter transactionParameter = new TransactionParameter();
    transactionParameter.setId(transactionParameterEntity.getId());
    transactionParameter.setLevyRate(transactionParameterEntity.getLevyRate());
    transactionParameter.setEffectiveDate(transactionParameterEntity.getEffectiveDate());
    return transactionParameter;
  }
}
