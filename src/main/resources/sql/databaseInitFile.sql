CREATE DATABASE IF NOT EXISTS BankDB;

use BankDB;

DROP TABLE IF EXISTS bank;
DROP TABLE IF EXISTS bank_account;
DROP TABLE IF EXISTS saving_account;


CREATE TABLE bank (
  accountid BIGINT PRIMARY KEY AUTO_INCREMENT,
  accountnumber BINARY(16) NOT NULL UNIQUE,
  balance DECIMAL(10,2) NOT NULL,
  accounttype INT NOT NULL
);

-- Table saving_account
CREATE TABLE saving_account (
  accountid BIGINT PRIMARY KEY,
  depositlimit DECIMAL(10,2) NOT NULL DEFAULT 1000,
  FOREIGN KEY (accountid) REFERENCES bank(accountid)
);


-- Table bank_account
CREATE TABLE bank_account (
  accountid BIGINT PRIMARY KEY,
  overdraft DECIMAL(10,2) NOT NULL DEFAULT 0,
  FOREIGN KEY (accountid) REFERENCES bank(accountid)
);


-- Table operation
CREATE TABLE operation (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  accountid BIGINT NOT NULL,
  typeoperation INT NOT NULL,  -- Replace with your actual operation types
  montant DECIMAL(10,2) NOT NULL,
  accounttype INT NOT NULL,
  dateoperation DATETIME NOT NULL,
  FOREIGN KEY (accountid) REFERENCES bank(accountid)
);