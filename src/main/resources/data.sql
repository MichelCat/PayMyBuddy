-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Default values for testing functionality
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------


insert into app_user (user_email, user_password, user_role, user_expired, user_locked, user_credentials_expired, user_enabled) values
('guto@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'USER_ROLE', false, false, false, true)
, ('hayley@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'USER_ROLE', false, false, false, true)
, ('clara@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'USER_ROLE', false, false, false, true)
, ('smith@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'USER_ROLE', false, false, false, true)
, ('alex@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'USER_ROLE', false, false, false, true)
, ('bill@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'USER_ROLE', false, false, false, true)
, ('dave@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'USER_ROLE', false, false, false, true)
, ('dan@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'USER_ROLE', false, false, false, true)
, ('admin@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'ADMIN_ROLE', false, false, false, true)
, ('contact@gmail.com', '$2a$10$mrfxRneNX/SdXiXSgcg9g.xK/Hhat/kYH3at8OZdCbZjTWNIfV9Pq', 'ADMIN_ROLE', false, false, false, true);

set @idGuto=(select id_user from app_user where user_email='guto@gmail.com');
set @idHayley=(select id_user from app_user where user_email='hayley@gmail.com');
set @idClara=(select id_user from app_user where user_email='clara@gmail.com');
set @idSmith=(select id_user from app_user where user_email='smith@gmail.com');
set @idAlex=(select id_user from app_user where user_email='alex@gmail.com');
set @idBill=(select id_user from app_user where user_email='bill@gmail.com');
set @idDave=(select id_user from app_user where user_email='dave@gmail.com');
set @idDan=(select id_user from app_user where user_email='dan@gmail.com');

insert into customer (customer_id_user, customer_first_name, customer_last_name, customer_address_1, customer_address_2, customer_zip_code, customer_city) values
(@idGuto, 'guto', 'GUTO', 'adresse 1 guto', 'adresse 2 guto', '68007', 'ville 68007')
, (@idHayley, 'hayley', 'HAYLEY', 'adresse 1 hayley', 'adresse 2 hayley', '68000', 'ville 68000')
, (@idClara, 'clara', 'CLARA', 'adresse 1 clara', 'adresse 2 clara', '68001', 'ville 68001')
, (@idSmith, 'smith', 'SMITH', 'adresse 1 smith', 'adresse 2 smith', '68002', 'ville 68002')
, (@idAlex, 'alex', 'ALEX', 'adresse 1 alex', 'adresse 2 alex', '68003', 'ville 68003')
, (@idBill, 'bill', 'BILL', 'adresse 1 bill', 'adresse 2 bill', '68004', 'ville 68004')
, (@idDave, 'dave', 'DAVE', 'adresse 1 dave', 'adresse 2 dave', '68005', 'ville 68005')
, (@idDan, 'dan', 'DAN', 'adresse 1 dan', 'adresse 2 dan', '68006', 'ville 68006');

insert into buddy (buddy_id_user, buddy_id_buddy, buddy_connection) values
(@idGuto, @idHayley, 'Hayley')
, (@idGuto, @idClara, 'Clara')
, (@idGuto, @idSmith, 'Smith')
, (@idHayley, @idGuto, 'Guto')
, (@idHayley, @idClara, 'Clara')
, (@idClara, @idGuto, 'Guto')
, (@idClara, @idHayley, 'Hayley')
, (@idSmith, @idGuto, 'Guto');

insert into bank_transaction (transaction_id_debit, transaction_id_credit, transaction_date, transaction_description, transaction_amount, transaction_levy) values
(@idGuto, @idHayley, CURRENT_TIMESTAMP(6), 'Restaurant bill share', 10.0, 10.0 * 0.005)
, (@idGuto, @idClara, CURRENT_TIMESTAMP(6), 'Trip money', 25.0, 25.0 * 0.005)
, (@idGuto, @idSmith, CURRENT_TIMESTAMP(6), 'Movie tickets', 8.0, 8.0 * 0.005)
, (@idHayley, @idClara, CURRENT_TIMESTAMP(6), 'Restaurant', 10.0, 10.0 * 0.005)
, (@idHayley, @idClara, CURRENT_TIMESTAMP(6), 'Movie tickets', 8.0, 8.0 * 0.005)
, (@idClara, @idGuto, CURRENT_TIMESTAMP(6), 'Restaurant', 12.0, 12.0 * 0.005)
, (@idSmith, @idGuto, CURRENT_TIMESTAMP(6), 'Restaurant', 14.0, 14.0 * 0.005);

insert into customer_account (account_id_customer, account_balance) values
(@idGuto, 200)
, (@idHayley, 300)
, (@idClara, 400)
, (@idSmith, 500)
, (@idAlex, 200)
, (@idBill, 200)
, (@idDave, 200)
, (@idDan, 200);

insert into transaction_log (log_id_debit, log_id_credit, log_date, log_description, log_amount, log_levy)
select transaction_id_debit, transaction_id_credit, transaction_date, transaction_description, transaction_amount, transaction_amount * 0.005
from bank_transaction;

insert into bank_account (bank_id_customer, bank_name, bank_code, bank_branch_code, bank_account_number, bank_rib_key, bank_iban, bank_bic) values
(@idGuto, 'Banque de France', '30001', '00794', '12345678901', '85', 'FR7630001007941234567890185', 'BDFEFR2L')
, (@idHayley, 'Banque de France', '30002', '00795', '12345678902', '86', 'FR7630001007941234567890186', 'BDFEFR3L')
, (@idClara, 'Banque de France', '30003', '00796', '12345678903', '87', 'FR7630001007941234567890187', 'BDFEFR4L')
, (@idSmith, 'Banque de France', '30004', '00797', '12345678904', '88', 'FR7630001007941234567890188', 'BDFEFR5L')
, (@idAlex, 'Banque de France', '30005', '00798', '12345678905', '88', 'FR7630001007941234567890189', 'BDFEFR5L')
, (@idBill, 'Banque de France', '30006', '00799', '12345678906', '88', 'FR7630001007941234567890190', 'BDFEFR5L')
, (@idDave, 'Banque de France', '30007', '00799', '12345678906', '88', 'FR7630001007941234567890191', 'BDFEFR5L')
, (@idDan, 'Banque de France', '30008', '00800', '12345678906', '88', 'FR7630001007941234567890192', 'BDFEFR5L');

insert into transaction_parameter (effective_date, levy_rate, contact_email) values
( DATE_ADD(CURRENT_DATE(), INTERVAL -2 MONTH), 0.001, 'contact@gmail.com')
, ( DATE_ADD(CURRENT_DATE(), INTERVAL -1 MONTH), 0.002, 'contact@gmail.com')
, ( CURRENT_DATE(), 0.005, 'contact@gmail.com');
