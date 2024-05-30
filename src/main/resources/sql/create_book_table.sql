DROP TABLE IF EXISTS book;



CREATE TABLE public.book
(
    id BIGSERIAL UNIQUE PRIMARY KEY,
    author VARCHAR(100),
    title VARCHAR(100),
    isbn VARCHAR(100),
    publication_date DATE,
    deleted BOOLEAN,
    price DECIMAL,
    UNIQUE (id, isbn, deleted)
);