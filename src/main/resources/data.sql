INSERT INTO target (target_id, type, min_loan_count, max_loan_count, min_loan_allowed, max_loan_allowed, rate, max_amount) VALUES (1, 'NEW', 0, 2, 0, 100000, 0.15, 500000);
INSERT INTO target (target_id, type, min_loan_count, max_loan_count, min_loan_allowed, max_loan_allowed, rate, max_amount) VALUES (2, 'FREQUENT', 2, 5, 100000, 500000, 0.10, 1000000);
INSERT INTO target (target_id, type, min_loan_count, max_loan_count, min_loan_allowed, max_loan_allowed, rate, max_amount) VALUES (3, 'PREMIUM', 5, 1000, 500000, 100000, 0.05, 5000000);


INSERT INTO customer (first_name, last_name, target) VALUES ('Andres', 'Velez', 'NEW');
INSERT INTO customer (first_name, last_name, target) VALUES ('Juliana', 'Garcia', 'FREQUENT');
INSERT INTO customer (first_name, last_name, target) VALUES ('Luz Maria', 'Gomez', 'PREMIUM');
INSERT INTO customer (first_name, last_name, target) VALUES ('Oscar', 'Martinez', 'NEW');
INSERT INTO customer (first_name, last_name, target) VALUES ('James', 'Arango', 'PREMIUM');
INSERT INTO customer (first_name, last_name, target) VALUES ('Diana', 'Ospina', 'PREMIUM');


INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('5000', 2, 0.15, 1, 'NEW', '2019-04-01 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 2, 'NEW', '2019-04-02 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('2000', 2, 0.15, 3, 'NEW', '2019-05-15 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('10000', 2, 0.10, 4, 'NEW', '2019-05-25 16:59:08.513','OPEN');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 5, 'NEW', '2019-06-05 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 6, 'NEW', '2019-06-25 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 1, 'NEW', '2019-07-01 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 2, 'NEW', '2019-07-02 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 3, 'NEW', '2019-08-15 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('3000', 2, 0.10, 4, 'NEW', '2019-08-25 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 5, 'NEW', '2019-09-05 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('4000', 2, 0.15, 6, 'NEW', '2019-09-25 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('5000', 2, 0.15, 1, 'FREQUENT', '2020-04-01 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 2, 'FREQUENT', '2020-04-02 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('2000', 2, 0.15, 3, 'FREQUENT', '2020-05-15 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('10000', 2, 0.10, 4, 'FREQUENT', '2020-05-25 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 5, 'FREQUENT', '2020-06-05 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 6, 'FREQUENT', '2020-06-25 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 1, 'FREQUENT', '2020-07-01 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 2, 'FREQUENT', '2020-07-02 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 3, 'FREQUENT', '2020-08-15 16:59:08.513','CLOSED');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('3000', 2, 0.10, 4, 'FREQUENT', '2020-08-25 16:59:08.513','OPEN');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 2, 0.15, 5, 'FREQUENT', '2020-09-05 16:59:08.513','OPEN');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('4000', 2, 0.15, 6, 'FREQUENT', '2020-09-25 16:59:08.513','OPEN');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('1000', 4, 0.05, 1, 'PREMIUM', '2022-05-05 16:59:08.513','OPEN');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('2000', 4, 0.05, 3, 'PREMIUM', '2022-06-05 16:59:08.513','OPEN');
INSERT INTO loan (amount, term, rate, user_id, target, date, status) VALUES ('5000', 4, 0.05, 5, 'PREMIUM', '2022-07-05 16:59:08.513','OPEN');

INSERT INTO payment (amount, loan_id, date) VALUES (500,4, '2019-06-25 16:59:08.513');
INSERT INTO payment (amount, loan_id, date) VALUES (700,4, '2019-07-25 16:59:08.513');

