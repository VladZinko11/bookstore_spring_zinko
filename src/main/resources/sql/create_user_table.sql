CREATE TABLE public.users
(
    id BIGSERIAL UNIQUE PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255) check (role in ( 'ADMIN', 'CUSTOMER', 'MANAGER' )),
    deleted BOOLEAN
);