package com.paymybuddy.paymybuddy.controller.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerAccountEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.CustomerEntity;
import com.paymybuddy.paymybuddy.controller.model.CustomerAccount;

@SpringBootTest
class CustomerAccountMapperTest {
  
  @Autowired
  private CustomerAccountMapper customerAccountMapper;
  
  /**
   * General case test, Mapper CustomerAccountEntity to CustomerAccount
   */
  @Test
  void mapperCustomerAccountEntityToPerson_CustomerAccount_returnCustomerAccount() {
    // GIVEN
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(2);
    
    CustomerAccountEntity customerAccountEntity = new CustomerAccountEntity();
    customerAccountEntity.setId(1);
    customerAccountEntity.setCustomer(customerEntity);
    customerAccountEntity.setBalance(200f);
    // WHEN
    CustomerAccount result = customerAccountMapper.mapEntityToModel(customerAccountEntity);
    // THEN
    assertThat(result.getId()).isEqualTo(customerAccountEntity.getId());
    assertThat(result.getIdCustomer()).isEqualTo(customerEntity.getId());
    assertThat(result.getId()).isEqualTo(customerAccountEntity.getId());
  }

}
