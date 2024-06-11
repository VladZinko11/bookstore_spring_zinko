DROP TABLE IF EXISTS public.order_items;

CREATE TABLE public.order_items
(
    id BIGSERIAL UNIQUE PRIMARY KEY,
    book_id BIGSERIAL,
    quantity integer,
    order_id BIGSERIAL,
    deleted BOOLEAN
);