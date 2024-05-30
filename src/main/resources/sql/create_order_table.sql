DROP TABLE IF EXISTS public.order;
DROP TABLE IF EXISTS public.enum_status;

CREATE TABLE public.order
(
    id BIGSERIAL UNIQUE PRIMARY KEY,
    user_id BIGINT,
    cost NUMERIC,
    status_id BIGINT,
    deleted BOOLEAN
);

CREATE TABLE public.enum_status
(
    id BIGSERIAL UNIQUE PRIMARY KEY,
    status VARCHAR(30)
);