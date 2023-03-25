package com.paymybuddy.paymybuddy.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.paymybuddy.paymybuddy.dao.db.PersonDao;
import com.paymybuddy.paymybuddy.dao.db.entities.BuddyEntity;
import com.paymybuddy.paymybuddy.dao.db.entities.PersonEntity;

public class PersonBussiness {

  @Autowired
  private PersonDao personDao;

  public PersonEntity getPersonByName(String personEmail) {
    return personDao.findByPersonEmail(personEmail);
  }

  public List<BuddyEntity> getPersonByBuddyEntityUsers(Integer idPerson) {
    return personDao.findById(idPerson).get().getBuddyEntityUsers();
  }
  
  public PersonEntity saveProduct(PersonEntity personEntity) {
    return personDao.save(personEntity);
  }
}
