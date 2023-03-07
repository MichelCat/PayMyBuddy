package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.UserAccountEntity;

public interface UserAccountEntityDao extends JpaRepository<UserAccountEntity, Integer> {

}
