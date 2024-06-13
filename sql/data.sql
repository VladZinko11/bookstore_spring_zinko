CREATE TABLE books
(
    id BIGSERIAL PRIMARY KEY,
    author VARCHAR(255),
    title VARCHAR(255),
    isbn VARCHAR(255),
    publication_date DATE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    price NUMERIC(5,2)
);

CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255) check (role in ( 'ADMIN', 'CUSTOMER', 'MANAGER' )),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE orders
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users,
    status VARCHAR(255) check (status in ('ISSUED' , 'PROCESSED', 'DELIVERED')),
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE order_items
(
    id BIGSERIAL PRIMARY KEY,
    book_id BIGSERIAL REFERENCES books,
    quantity integer,
    order_id BIGSERIAL REFERENCES orders,
    deleted BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO users(
    first_name, last_name, email, password, role
) VALUES ('Vlad', 'Zinko', 'zinko1996@yandex.ru', '12345678', 'ADMIN'),
         ('Max', 'Holloway', 'holloway@gmail.ru', md5(random()::text)::char(10), 'MANAGER'),
         ('Dustin', 'Poirier', 'poirier@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Justin', 'Gaethje', 'gaethje@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Charles', 'Oliveira', 'oliveira@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Arman', 'Tsarukyan', 'tsarukyan@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Mateusz', 'Gamrot', 'gamrot@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Beneil', 'Dariush', 'dariush@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Michael', 'Chandler', 'chandler@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Rafael', 'Fiziev', 'fisiev@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Renato', 'Moicano', 'moicano@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Dan', 'Hooker', 'hooker@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Jalin', 'Turner', 'turner@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Benoit', 'Saint Denis', 'benoit@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Rafael', 'Dos Anjos', 'rafael@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Bobby', 'Green', 'green@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Ilia', 'Topuria', 'topuria@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Brian', 'Ortega', 'ortega@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Yair', 'Rodriguez', 'rodriguez@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER'),
         ('Arnold', 'Alen', 'alen@gmail.ru', md5(random()::text)::char(10), 'CUSTOMER');

INSERT INTO books(
    author, title, isbn, publication_date, price)
VALUES
    ('Джоана Роулинг', 'Гарри Потер и узник Аскабана', '978-5-389-17021-6', '01-01-2019', 10),
    ('Маргарет Митчел', 'Унесенные ветром', '978-5-9603-0914-1', '01-01-2023', 15),
    ('Стивен Кинг', 'Зеленая Миля', '978-5-17-151384-9', '01-01-2022', 20),
    ('Артур Конан Дойл', 'Весь Шерлок Холмс (сборник)', '978-5-17-105207-2', '01-01-2019', 10),
    ('Джон Р. Р. Толкин', 'Властелин Колец: Возвращение короля', '978-5-17-133632-5', '01-01-2020', 15),
    ('Александр Дюма', 'Граф Монте-Кристо. В 2 томах', '978-5-04-118763-7', '01-01-2021', 20),
    ('Джордж Мартин', 'Буря Мечей', '978-5-17-123459-1', '01-01-2020', 10),
    ('Борис Васильев', 'В списках не значился', '978-5-8475-1359-3', '01-01-2021', 15),
    ('Марио Пьюзо', 'Крестный Отец', '978-5-04-185900-8', '01-01-2023', 20),
    ('Эрих Мария Ремарк', 'Искра жизни', '9785171187514', '01-01-2019', 10),
    ('Стивен Кинг', 'Побег из Шоушенка', '978-5-21929-0', '01-01-2011', 15),
    ('Эрих Мария Ремарк', 'На западном фронте без перемен', '978-5-17-156264-9', '01-01-2023', 20),
    ('Михаил Булгаков', 'Масте и маргарита', '978-5-04-178314-3', '01-01-2024', 10),
    ('Михаил Булгаков', 'Записки юного врача (сборник)', '978-966-03-6215-4', '01-01-2013', 15),
    ('Джордж Мартин', 'Танец с драконами. Искры над пеплом', '978-5-17-078124-9', '01-01-2015', 20),
    ('Агата Кристи', 'Убийство в восточном экспрессе', '9785041563325', '01-01-2021', 10),
    ('Борис Акунин', 'Алмазная Колесница', '978-5-8159-1419-3', '01-01-2016', 15),
    ('Джек Лондон', 'Мартин Иден', '978-5-04-177951-1', '01-01-2023', 20),
    ('Эрих Мария Ремарк', 'Три товарища', '978-5-17-112638-4', '01-01-2019', 10),
    ('Джек Лондон', 'Белый Клык', '978-5-04-177953-5', '01-01-2023', 15);

INSERT INTO orders(user_id, status)
VALUES (1, 'PROCESSED'),
       (1, 'DELIVERED'),
       (1, 'ISSUED'),
       (2, 'PROCESSED'),
       (3, 'DELIVERED'),
       (4, 'ISSUED'),
       (5, 'PROCESSED'),
       (6, 'DELIVERED'),
       (7, 'ISSUED'),
       (8, 'PROCESSED'),
       (9, 'DELIVERED'),
       (10, 'ISSUED'),
       (11, 'PROCESSED'),
       (12, 'DELIVERED'),
       (13, 'ISSUED'),
       (14, 'PROCESSED'),
       (15, 'DELIVERED'),
       (16, 'ISSUED'),
       (17, 'PROCESSED'),
       (18, 'DELIVERED'),
       (19, 'ISSUED'),
       (20, 'PROCESSED'),
       (2, 'DELIVERED'),
       (4, 'ISSUED'),
       (20, 'PROCESSED');

INSERT INTO order_items (book_id, quantity, order_id)
VALUES (1 , 20, 1),
       (2 , 20, 2),
       (3 , 20, 3),
       (4 , 20, 4),
       (5 , 20, 5),
       (6 , 20, 6),
       (7 , 20, 7),
       (8, 20, 8),
       (9, 20, 9),
       (10, 20, 10),
       (11, 20, 11),
       (12, 20, 12),
       (13, 20, 13),
       (14, 20, 14),
       (15, 20, 15),
       (16, 20, 16),
       (17, 20, 17),
       (18, 20, 18),
       (19, 20, 19),
       (20, 20, 20),
       (1, 20, 21),
       (2, 20, 22),
       (3, 20, 23),
       (4, 20, 24),
       (5 , 20, 25),
       (6 , 20, 1),
       (7, 20, 1),
       (8, 20, 2),
       (9, 20, 3),
       (10, 20, 4);
