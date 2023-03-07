-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Setting up PayMyBuddyProd DB
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
drop database if exists PayMyBuddyProd;

create database PayMyBuddyProd;
use PayMyBuddyProd;

-- Table of users
create table person (
	id int PRIMARY KEY AUTO_INCREMENT,					-- User ID
	person_connection varchar(50) NOT NULL,				-- User connection
	person_email varchar(50) NOT NULL,					-- User email
	person_password varchar(50) NOT NULL,				-- User password
	constraint uc_person_email UNIQUE KEY (person_email)
);

-- Buddy relationship join table
create table buddy (
	buddy_id_user int NOT NULL,							-- User ID
	buddy_id_buddy int NOT NULL,						-- Friend ID
	constraint pk_buddy PRIMARY KEY (buddy_id_user, buddy_id_buddy),
	constraint fk_buddy_person_user FOREIGN KEY (buddy_id_user) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE,
	constraint fk_buddy_person_buddy FOREIGN KEY (buddy_id_buddy) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE
);

-- User balances table
create table user_account(
	account_id_user int NOT NULL,						-- User account ID
	account_balance float,								-- Account balance
	constraint pk_user_account PRIMARY KEY (account_id_user),
	constraint fk_user_account_person FOREIGN KEY (account_id_user) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE
);

-- Table of user transactions
create table bank_transaction (
	id int PRIMARY KEY AUTO_INCREMENT,					-- Bank transaction ID
	transaction_id_debtor int NOT NULL,					-- Debit User ID
	transaction_id_credit int NOT NULL,					-- Credit user ID
	transaction_date timestamp(6) NOT NULL,				-- Transaction date
	transaction_description varchar(50),				-- Transaction description
	transaction_amount float,							-- Transaction amount
	constraint fk_bank_transaction_person_debtor FOREIGN KEY (transaction_id_debtor) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE,
	constraint fk_bank_transaction_person_credit FOREIGN KEY (transaction_id_credit) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE,
	constraint ck_bank_transaction_amount check (transaction_amount > 0)
);

-- Table of user accounts
create table bank_account (
	id int PRIMARY KEY AUTO_INCREMENT,					-- Bank account ID
	bank_id_user int NOT NULL,							-- Account User ID
	bank_name varchar(50) NOT NULL,						-- Name of the bank
	bank_code varchar(5) NOT NULL,						-- Bank code
	bank_branch_code varchar(5) NOT NULL,				-- Branch code
	bank_account_number varchar(11) NOT NULL,			-- Account number
	bank_rib_key varchar(11) NOT NULL,					-- Bank key
	bank_iban varchar(34) NOT NULL,						-- IBAN
	bank_bic varchar(11) NOT NULL,						-- BIC code
	constraint uc_bank_account_account UNIQUE KEY (bank_code, bank_branch_code, bank_account_number, bank_rib_key),
	constraint uc_bank_account_iban UNIQUE KEY (bank_iban, bank_bic),
	constraint fk_bank_account_person FOREIGN KEY (bank_id_user) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE
);

-- User levy log
create table levy_log (
	id int PRIMARY KEY AUTO_INCREMENT,					-- Levy log ID
	levy_id_user int NOT NULL,							-- Levy user ID
	levy_date timestamp(6) NOT NULL,					-- Levy date
	levy_description varchar(50),						-- Levy description
	levy_amount float,									-- Levy amount
	constraint fk_levy_log_person FOREIGN KEY (levy_id_user) REFERENCES person (id),
	constraint ck_levy_log_amount check (levy_amount > 0)
);

-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Default values for testing functionality
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
use PayMyBuddyProd;

start transaction;

insert into person (person_connection, person_email, person_password) values
('Guto', 'Guto@gmail.com', 'motPasseGuto')
, ('Hayley', 'hayley@gmail.com', 'motPasseHayley')
, ('Clara', 'clara@gmail.com', 'motPasseClara')
, ('Smith', 'smith@gmail.com', 'motPasseSmith');

set @idGuto=(select id from person where person_connection='Guto');
set @idHayley=(select id from person where person_connection='Hayley');
set @idClara=(select id from person where person_connection='Clara');
set @idSmith=(select id from person where person_connection='Smith');

insert into buddy (buddy_id_user, buddy_id_buddy) values
(@idGuto, @idHayley)
, (@idGuto, @idClara)
, (@idGuto, @idSmith);

insert into bank_transaction (transaction_id_debtor, transaction_id_credit, transaction_date, transaction_description, transaction_amount) values
(@idGuto, @idHayley, CURRENT_TIMESTAMP(6), 'Restaurant bill share', 10.0)
, (@idGuto, @idClara, CURRENT_TIMESTAMP(6), 'Trip money', 25.0)
, (@idGuto, @idSmith, CURRENT_TIMESTAMP(6), 'Movie tickets', 8.0);

insert into user_account (account_id_user, account_balance) values
(@idGuto, 200);

insert into levy_log (levy_id_user, levy_date, levy_description, levy_amount)
select transaction_id_debtor, transaction_date, transaction_description, transaction_amount * 0.005
from bank_transaction;

insert into bank_account (bank_id_user, bank_name, bank_code, bank_branch_code, bank_account_number, bank_rib_key, bank_iban, bank_bic) values
(@idGuto, 'Banque de France', '30001', '00794', '12345678901', '85', 'FR7630001007941234567890185', 'BDFEFR2L');

commit;

-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Setting up PayMyBuddyTest DB
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
drop database if exists PayMyBuddyTest;

create database PayMyBuddyTest;
use PayMyBuddyTest;

-- Table of users
create table person (
	id int PRIMARY KEY AUTO_INCREMENT,					-- User ID
	person_connection varchar(50) NOT NULL,				-- User connection
	person_email varchar(50) NOT NULL,					-- User email
	person_password varchar(50) NOT NULL,				-- User password
	constraint uc_person_email UNIQUE KEY (person_email)
);

-- Buddy relationship join table
create table buddy (
	buddy_id_user int NOT NULL,							-- User ID
	buddy_id_buddy int NOT NULL,						-- Friend ID
	constraint pk_buddy PRIMARY KEY (buddy_id_user, buddy_id_buddy),
	constraint fk_buddy_person_user FOREIGN KEY (buddy_id_user) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE,
	constraint fk_buddy_person_buddy FOREIGN KEY (buddy_id_buddy) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE
);

-- User balances table
create table user_account(
	account_id_user int NOT NULL,						-- User account ID
	account_balance float,								-- Account balance
	constraint pk_user_account PRIMARY KEY (account_id_user),
	constraint fk_user_account_person FOREIGN KEY (account_id_user) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE
);

-- Table of user transactions
create table bank_transaction (
	id int PRIMARY KEY AUTO_INCREMENT,					-- Bank transaction ID
	transaction_id_debtor int NOT NULL,					-- Debit User ID
	transaction_id_credit int NOT NULL,					-- Credit user ID
	transaction_date timestamp(6) NOT NULL,				-- Transaction date
	transaction_description varchar(50),				-- Transaction description
	transaction_amount float,							-- Transaction amount
	constraint fk_bank_transaction_person_debtor FOREIGN KEY (transaction_id_debtor) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE,
	constraint fk_bank_transaction_person_credit FOREIGN KEY (transaction_id_credit) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE,
	constraint ck_bank_transaction_amount check (transaction_amount > 0)
);

-- Table of user accounts
create table bank_account (
	id int PRIMARY KEY AUTO_INCREMENT,					-- Bank account ID
	bank_id_user int NOT NULL,							-- Account User ID
	bank_name varchar(50) NOT NULL,						-- Name of the bank
	bank_code varchar(5) NOT NULL,						-- Bank code
	bank_branch_code varchar(5) NOT NULL,				-- Branch code
	bank_account_number varchar(11) NOT NULL,			-- Account number
	bank_rib_key varchar(11) NOT NULL,					-- Bank key
	bank_iban varchar(34) NOT NULL,						-- IBAN
	bank_bic varchar(11) NOT NULL,						-- BIC code
	constraint uc_bank_account_account UNIQUE KEY (bank_code, bank_branch_code, bank_account_number, bank_rib_key),
	constraint uc_bank_account_iban UNIQUE KEY (bank_iban, bank_bic),
	constraint fk_bank_account_person FOREIGN KEY (bank_id_user) REFERENCES person (id)
		on delete CASCADE
		on update CASCADE
);

-- User levy log
create table levy_log (
	id int PRIMARY KEY AUTO_INCREMENT,					-- Levy log ID
	levy_id_user int NOT NULL,							-- Levy user ID
	levy_date timestamp(6) NOT NULL,					-- Levy date
	levy_description varchar(50),						-- Levy description
	levy_amount float,									-- Levy amount
	constraint fk_levy_log_person FOREIGN KEY (levy_id_user) REFERENCES person (id),
	constraint ck_levy_log_amount check (levy_amount > 0)
);
