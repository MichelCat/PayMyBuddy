-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------
-- Setting up PayMyBuddyTest DB
-- -----------------------------------------------------------------------------
-- -----------------------------------------------------------------------------


-- Table of users
create table app_user (
	id_user int PRIMARY KEY AUTO_INCREMENT,						-- User ID
	user_email varchar(254) NOT NULL,							-- Email used to authenticate the user
	user_password varchar(100) NOT NULL,						-- Password used to authenticate the user
    user_role varchar(20),										-- User role
	user_expired boolean,										-- User account expired
	user_locked boolean,										-- User locked
	user_credentia_expired boolean,								-- User credentials (password) expired
	user_enabled boolean,										-- Activated user
	user_email_validation_key varchar(36),						-- Email validation key
	user_valid_email_end_date datetime,							-- Valid email end date
	constraint uc_user_email UNIQUE KEY (user_email),
	constraint uc_user_email_validation_key UNIQUE KEY (user_email_validation_key)
);

-- Table of customers
create table customer (
	id_customer int PRIMARY KEY AUTO_INCREMENT,					-- Customer ID
	customer_id_user int NOT NULL,								-- User ID
	customer_first_name varchar(50) NOT NULL default '',		-- Customer first name
	customer_last_name varchar(50) NOT NULL default '',			-- Customer last name
	customer_address_1 varchar(100) NOT NULL default '',		-- Address 1 customer
	customer_address_2 varchar(100) NOT NULL default '',		-- Address 2 customer
	customer_zip_code varchar(50) NOT NULL default '',			-- Customer zip code
	customer_city varchar(50) NOT NULL default '',				-- Customer city
	constraint fk_customer_user FOREIGN KEY (customer_id_user) REFERENCES app_user (id_user)
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
	id_customer_account int PRIMARY KEY AUTO_INCREMENT,		-- Customer account ID
	account_id_customer int NOT NULL,						-- Account customer ID
	account_balance float NOT NULL,							-- Account balance
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
	bank_rib_key varchar(2) NOT NULL,					-- Bank key
	bank_iban varchar(34) NOT NULL,						-- IBAN
	bank_bic varchar(11) NOT NULL,						-- BIC code
	constraint fk_bank_account_customer FOREIGN KEY (bank_id_customer) REFERENCES customer (id_customer)
);

-- Table of customer transaction log
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

-- Table of transaction parameter
create table transaction_parameter (
	id_parameter int PRIMARY KEY AUTO_INCREMENT,		-- Transaction parameter ID
    effective_date date NOT NULL,						-- Effective date
	levy_rate float NOT NULL,							-- Levy rate
	contact_email varchar(254) NOT NULL,				-- Contact email
	constraint uc_transaction_parameter_effective_date UNIQUE KEY (effective_date)
);

-- Customer message table
create table customer_message (
	id_message int PRIMARY KEY AUTO_INCREMENT,				-- Message ID
	message_id_sender int NOT NULL,							-- Email sender ID
	message_id_recipient int NOT NULL,						-- Email recipient ID
	message_date timestamp(6) NOT NULL,						-- Message date
	message_subject varchar(100) NOT NULL,					-- Subject
	message_detail varchar(500) NOT NULL,					-- Detail
	constraint fk_message_customer_sender FOREIGN KEY (message_id_sender) REFERENCES app_user (id_user),
	constraint fk_message_customer_recipient FOREIGN KEY (message_id_recipient) REFERENCES app_user (id_user)
);
