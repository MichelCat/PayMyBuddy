package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.PersonEntity;

public interface PersonEntityDao extends JpaRepository<PersonEntity, Integer> {

}
