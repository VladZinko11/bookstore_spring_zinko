DROP TABLE IF EXISTS books;



CREATE TABLE public.books
(
    id BIGSERIAL UNIQUE PRIMARY KEY,
    author VARCHAR(100),
    title VARCHAR(100),
    isbn VARCHAR(100),
    publication_date DATE,
    deleted BOOLEAN,
    price NUMERIC(38,2)
);