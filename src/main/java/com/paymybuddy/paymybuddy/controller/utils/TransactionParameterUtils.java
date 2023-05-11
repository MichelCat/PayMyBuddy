package com.paymybuddy.paymybuddy.controller.utils;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
  
  /**
   * Round the transaction levy amount to the nearest value
   * 
   * @param numberRound Number to round up
   * @param numberDecimal Number of decimal places
   * @return Rounded number
   */
  public float roundLevy(float numberRound) {
    int numberDecimal = 2;
    BigDecimal bigDecimal = new BigDecimal(numberRound);
    // Rounded to the nearest 2 decimal places
    bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
    return bigDecimal.floatValue();
  }
}
