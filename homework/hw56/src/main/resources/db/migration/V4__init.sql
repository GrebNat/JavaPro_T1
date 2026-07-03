CREATE TABLE products
(
    id           serial primary key,
    account      varchar(255),
    balance      decimal,
    product_type varchar,
    user_id      integer
);

INSERT INTO products (account, balance, product_type, user_id)
VALUES ('40817810938000012345', 50000.00, 'счет', 1),
       ('40817810938000012346', 150000.50, 'счет', 1),
       ('4111111111111111', 25000.75, 'карта', 2),
       ('4222222222222222', 10000.00, 'карта', 2),
       ('40817810938000012347', 75500.25, 'счет', 3),
       ('4333333333333333', 5000.00, 'карта', 3);
