DROP TABLE IF EXISTS public.order_item;

CREATE TABLE public.order_item
(
    book_id int8,
    quantity int4,
    price DECIMAL,
    order_id int8,
    deleted BOOLEAN
);