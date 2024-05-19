DROP TABLE IF EXISTS public.user;
DROP TABLE IF EXISTS public.enum_role;

CREATE TABLE public.enum_role (
    id BIGSERIAL UNIQUE PRIMARY KEY,
    role VARCHAR(8)
);
CREATE TABLE public.user
(
    id BIGSERIAL UNIQUE PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(50),
    password VARCHAR(20),
    id_enum_role BIGINT,
    deleted BOOLEAN,
    UNIQUE (id, email, deleted)
);