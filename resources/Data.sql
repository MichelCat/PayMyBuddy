-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Setting up PayMyBuddyProd DB
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
drop database if exists PayMyBuddyProd;

create database PayMyBuddyProd;
use PayMyBuddyProd;

-- Table of customers
create table customer (
	id_customer int PRIMARY KEY AUTO_INCREMENT,					-- Customer ID
	customer_email varchar(100) NOT NULL,						-- Customer email
	customer_password varchar(50) NOT NULL,						-- Customer password
	customer_first_name varchar(50) NOT NULL default '',		-- Customer first name
	customer_last_name varchar(50) NOT NULL default '',			-- Customer last name
	customer_address_1 varchar(100) NOT NULL default '',		-- Address 1 customer
	customer_address_2 varchar(100) NOT NULL default '',		-- Address 2 customer
	customer_zip_code varchar(50) NOT NULL default '',			-- Customer zip code
	customer_city varchar(50) NOT NULL default '',				-- Customer city
	constraint uc_customer_email UNIQUE KEY (customer_email)
);

-- Buddy relationship join table
create table buddy (
	id_buddy int PRIMARY KEY AUTO_INCREMENT,			-- Buddy ID
	buddy_id_user int NOT NULL,							-- User ID
	buddy_id_buddy int NOT NULL,						-- Friend ID
	buddy_connection varchar(50) NOT NULL,				-- Connection name
	constraint fk_buddy_customer_user FOREIGN KEY (buddy_id_user) REFERENCES customer (id_customer),
	constraint fk_buddy_customer_buddy FOREIGN KEY (buddy_id_buddy) REFERENCES customer (id_customer)
);

-- Customer balances table
create table customer_account(
	account_id_customer int PRIMARY KEY,				-- Customer account ID
	account_balance float NOT NULL,						-- Account balance
	constraint fk_customer_account_customer FOREIGN KEY (account_id_customer) REFERENCES customer (id_customer)
);

-- Table of customer transactions
create table bank_transaction (
	id_bank_transaction int PRIMARY KEY AUTO_INCREMENT,		-- Bank transaction ID
	transaction_id_debit int NOT NULL,						-- Debit customer ID
	transaction_id_credit int NOT NULL,						-- Credit customer ID
	transaction_date timestamp(6) NOT NULL,					-- Transaction date
	transaction_description varchar(50) NOT NULL,			-- Transaction description
	transaction_amount float NOT NULL,						-- Transaction amount
	transaction_levy float NOT NULL,						-- Transaction levy
	constraint fk_bank_transaction_customer_debit FOREIGN KEY (transaction_id_debit) REFERENCES customer (id_customer),
	constraint fk_bank_transaction_customer_credit FOREIGN KEY (transaction_id_credit) REFERENCES customer (id_customer)
);

-- Table of customer accounts
create table bank_account (
	id_bank_account int PRIMARY KEY AUTO_INCREMENT,		-- Bank account ID
	bank_id_customer int NOT NULL,						-- Account customer ID
	bank_name varchar(50) NOT NULL,						-- Name of the bank
	bank_code varchar(5) NOT NULL,						-- Bank code
	bank_branch_code varchar(5) NOT NULL,				-- Branch code
	bank_account_number varchar(11) NOT NULL,			-- Account number
	bank_rib_key varchar(11) NOT NULL,					-- Bank key
	bank_iban varchar(34) NOT NULL,						-- IBAN
	bank_bic varchar(11) NOT NULL,						-- BIC code
	constraint uc_bank_account_account UNIQUE KEY (bank_code, bank_branch_code, bank_account_number, bank_rib_key),
	constraint uc_bank_account_iban UNIQUE KEY (bank_iban, bank_bic),
	constraint fk_bank_account_customer FOREIGN KEY (bank_id_customer) REFERENCES customer (id_customer)
);

-- Customer transaction log
create table transaction_log (
	id_log int PRIMARY KEY AUTO_INCREMENT,				-- Log ID
	log_id_debit int NOT NULL,							-- Debit customer ID
	log_id_credit int NOT NULL,							-- Credit customer ID
	log_date timestamp(6) NOT NULL,						-- Log date
	log_description varchar(50) NOT NULL,				-- Log description
	log_amount float NOT NULL,							-- Log amount
	log_levy float NOT NULL,							-- Log levy
	constraint fk_transaction_log_customer_debit FOREIGN KEY (log_id_debit) REFERENCES customer (id_customer),
	constraint fk_transaction_log_customer_credit FOREIGN KEY (log_id_credit) REFERENCES customer (id_customer)
);

-- Transaction parameter
create table transaction_parameter (
	id_parameter int PRIMARY KEY AUTO_INCREMENT,		-- Transaction parameter ID
	levy_rate float NOT NULL							-- Levy rate
);

-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Default values for testing functionality
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
use PayMyBuddyProd;

start transaction;

insert into customer (customer_email, customer_password, customer_first_name, customer_last_name, customer_address_1, customer_address_2, customer_zip_code, customer_city) values
('guto@gmail.com', 'motPasseGuto', 'guto', 'name guto', 'adresse 1 guto', 'adresse 2 guto', '68007', 'ville 68007')
, ('hayley@gmail.com', 'motPasseHayley', 'hayley', 'name hayley', 'adresse 1 hayley', 'adresse 2 hayley', '68000', 'ville 68000')
, ('clara@gmail.com', 'motPasseClara', 'clara', 'name clara', 'adresse 1 clara', 'adresse 2 clara', '68001', 'ville 68001')
, ('smith@gmail.com', 'motPasseSmith', 'smith', 'name smith', 'adresse 1 smith', 'adresse 2 smith', '68002', 'ville 68002')
, ('alex@gmail.com', 'motPasseAlex', 'alex', 'name alex', 'adresse 1 alex', 'adresse 2 alex', '68003', 'ville 68003')
, ('bill@gmail.com', 'motPasseBill', 'bill', 'name bill', 'adresse 1 bill', 'adresse 2 bill', '68004', 'ville 68004')
, ('dave@gmail.com', 'motPasseDave', 'dave', 'name dave', 'adresse 1 dave', 'adresse 2 dave', '68005', 'ville 68005')
, ('dan@gmail.com', 'motPasseDan', 'dan', 'name dan', 'adresse 1 dan', 'adresse 2 dan', '68006', 'ville 68006');

set @idGuto=(select id_customer from customer where customer_email='guto@gmail.com');
set @idHayley=(select id_customer from customer where customer_email='hayley@gmail.com');
set @idClara=(select id_customer from customer where customer_email='clara@gmail.com');
set @idSmith=(select id_customer from customer where customer_email='smith@gmail.com');
set @idAlex=(select id_customer from customer where customer_email='alex@gmail.com');
set @idBill=(select id_customer from customer where customer_email='bill@gmail.com');
set @idDave=(select id_customer from customer where customer_email='dave@gmail.com');
set @idDan=(select id_customer from customer where customer_email='dan@gmail.com');

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

insert into transaction_parameter (levy_rate) values (0.005);

commit;

-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Setting up PayMyBuddyTest DB
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
drop database if exists PayMyBuddyTest;

create database PayMyBuddyTest;
use PayMyBuddyTest;

-- Table of customers
create table customer (
	id_customer int PRIMARY KEY AUTO_INCREMENT,					-- Customer ID
	customer_email varchar(100) NOT NULL,						-- Customer email
	customer_password varchar(50) NOT NULL,						-- Customer password
	customer_first_name varchar(50) NOT NULL default '',		-- Customer first name
	customer_last_name varchar(50) NOT NULL default '',			-- Customer last name
	customer_address_1 varchar(100) NOT NULL default '',		-- Address 1 customer
	customer_address_2 varchar(100) NOT NULL default '',		-- Address 2 customer
	customer_zip_code varchar(50) NOT NULL default '',			-- Customer zip code
	customer_city varchar(50) NOT NULL default '',				-- Customer city
	constraint uc_customer_email UNIQUE KEY (customer_email)
);

-- Buddy relationship join table
create table buddy (
	id_buddy int PRIMARY KEY AUTO_INCREMENT,			-- Buddy ID
	buddy_id_user int NOT NULL,							-- User ID
	buddy_id_buddy int NOT NULL,						-- Friend ID
	buddy_connection varchar(50) NOT NULL,				-- Connection name
	constraint fk_buddy_customer_user FOREIGN KEY (buddy_id_user) REFERENCES customer (id_customer),
	constraint fk_buddy_customer_buddy FOREIGN KEY (buddy_id_buddy) REFERENCES customer (id_customer)
);

-- Customer balances table
create table customer_account(
	account_id_customer int PRIMARY KEY,				-- Customer account ID
	account_balance float NOT NULL,						-- Account balance
	constraint fk_customer_account_customer FOREIGN KEY (account_id_customer) REFERENCES customer (id_customer)
);

-- Table of customer transactions
create table bank_transaction (
	id_bank_transaction int PRIMARY KEY AUTO_INCREMENT,		-- Bank transaction ID
	transaction_id_debit int NOT NULL,						-- Debit customer ID
	transaction_id_credit int NOT NULL,						-- Credit customer ID
	transaction_date timestamp(6) NOT NULL,					-- Transaction date
	transaction_description varchar(50) NOT NULL,			-- Transaction description
	transaction_amount float NOT NULL,						-- Transaction amount
	transaction_levy float NOT NULL,						-- Transaction levy
	constraint fk_bank_transaction_customer_debit FOREIGN KEY (transaction_id_debit) REFERENCES customer (id_customer),
	constraint fk_bank_transaction_customer_credit FOREIGN KEY (transaction_id_credit) REFERENCES customer (id_customer)
);

-- Table of customer accounts
create table bank_account (
	id_bank_account int PRIMARY KEY AUTO_INCREMENT,		-- Bank account ID
	bank_id_customer int NOT NULL,						-- Account customer ID
	bank_name varchar(50) NOT NULL,						-- Name of the bank
	bank_code varchar(5) NOT NULL,						-- Bank code
	bank_branch_code varchar(5) NOT NULL,				-- Branch code
	bank_account_number varchar(11) NOT NULL,			-- Account number
	bank_rib_key varchar(11) NOT NULL,					-- Bank key
	bank_iban varchar(34) NOT NULL,						-- IBAN
	bank_bic varchar(11) NOT NULL,						-- BIC code
	constraint uc_bank_account_account UNIQUE KEY (bank_code, bank_branch_code, bank_account_number, bank_rib_key),
	constraint uc_bank_account_iban UNIQUE KEY (bank_iban, bank_bic),
	constraint fk_bank_account_customer FOREIGN KEY (bank_id_customer) REFERENCES customer (id_customer)
);

-- Customer transaction log
create table transaction_log (
	id_log int PRIMARY KEY AUTO_INCREMENT,				-- Log ID
	log_id_debit int NOT NULL,							-- Debit customer ID
	log_id_credit int NOT NULL,							-- Credit customer ID
	log_date timestamp(6) NOT NULL,						-- Log date
	log_description varchar(50) NOT NULL,				-- Log description
	log_amount float NOT NULL,							-- Log amount
	log_levy float NOT NULL,							-- Log levy
	constraint fk_transaction_log_customer_debit FOREIGN KEY (log_id_debit) REFERENCES customer (id_customer),
	constraint fk_transaction_log_customer_credit FOREIGN KEY (log_id_credit) REFERENCES customer (id_customer)
);

-- Transaction parameter
create table transaction_parameter (
	id_parameter int PRIMARY KEY AUTO_INCREMENT,		-- Transaction parameter ID
	levy_rate float NOT NULL							-- Levy rate
);
