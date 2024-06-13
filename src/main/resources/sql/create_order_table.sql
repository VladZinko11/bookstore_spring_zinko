
CREATE TABLE public.orders
(
    id BIGSERIAL UNIQUE PRIMARY KEY,
    user_id BIGINT,
    status VARCHAR(255) check (status in ('ISSUED' , 'PROCESSED', 'DELIVERED')),
    deleted BOOLEAN
);
