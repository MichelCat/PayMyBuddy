package com.paymybuddy.paymybuddy.dao.db.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * BuddyEntity is Entity model
 * 
 * @author MC
 * @version 1.0
 */
@Data
@Entity
@Table(name = "buddy")
@FieldDefaults(level=AccessLevel.PRIVATE)
@NoArgsConstructor
@EqualsAndHashCode(of= {"buddyIdUser", "buddyIdBuddy"})
@ToString(callSuper = true)
public class BuddyEntity {
  /**
   * User ID
   */
  @Id
  Integer buddyIdUser;
  /**
   * Friend ID
   */
  @Id
  Integer buddyIdBuddy;
  
  @ManyToOne
  @JoinColumn(name="buddyIdUser", nullable=false)
  private PersonEntity personEntityUser;

  @ManyToOne
  @JoinColumn(name="buddyIdBuddy", nullable=false)
  private PersonEntity personEntityBuddy;
}
