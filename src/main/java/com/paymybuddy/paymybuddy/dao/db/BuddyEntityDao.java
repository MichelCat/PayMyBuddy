package com.paymybuddy.paymybuddy.dao.db;

import org.springframework.data.jpa.repository.JpaRepository;
import com.paymybuddy.paymybuddy.dao.db.entities.BuddyEntity;

public interface BuddyEntityDao extends JpaRepository<BuddyEntity, Integer> {

}
