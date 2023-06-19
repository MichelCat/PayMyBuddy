-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Default values for testing functionality
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------


-- Creation of an admin user and a contact user.
insert into app_user (user_email, user_password, user_role, user_expired, user_locked, user_credentials_expired, user_enabled) values
('admin@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'ADMIN_ROLE', false, false, false, true)
, ('contact@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'ADMIN_ROLE', false, false, false, true);

-- Add direct debit rates with effective date
insert into transaction_parameter (effective_date, levy_rate, contact_email) values
( DATE_ADD(CURRENT_DATE(), INTERVAL -2 MONTH), 0.001, 'contact@gmail.com')
, ( DATE_ADD(CURRENT_DATE(), INTERVAL -1 MONTH), 0.002, 'contact@gmail.com')
, ( CURRENT_DATE(), 0.005, 'contact@gmail.com');
