INSERT INTO bank (accountnumber, balance, accounttype)
VALUES
  (UUID_TO_BIN(UUID()), 5000.00, 2),
  (UUID_TO_BIN(UUID()), 10000.00, 2),
  (UUID_TO_BIN(UUID()), 7500.00, 2),
  (UUID_TO_BIN(UUID()), 2000.00, 1),
  (UUID_TO_BIN(UUID()), 3500.00, 1),
  (UUID_TO_BIN(UUID()), 6000.00, 1);

INSERT INTO bank_account (accountid, overdraft)
VALUES
  (1, 500.00),
  (2, 1000.00),
  (3, 800.00);

INSERT INTO saving_account (accountid, depositlimit)
VALUES
  (4, 10000.00),
  (5, 12000.00),
  (6, 30000.00);

INSERT INTO operation (accountid, typeoperation, montant, accounttype, dateoperation)
VALUES
  (1, 2, 250.00, 1, '2024-05-11 16:49:00'),
  (2, 1, 100.00, 1, '2024-05-11 16:49:00'),
  (3, 2, 500.00, 1, '2024-05-11 16:49:00'),
  (4, 1, 300.00, 2, '2024-05-11 16:49:00'),
  (5, 2, 150.00, 2, '2024-05-11 16:49:00'),
  (6, 1, 200.00, 2, '2024-05-11 16:49:00');