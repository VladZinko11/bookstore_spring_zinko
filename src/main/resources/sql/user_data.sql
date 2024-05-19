INSERT INTO public.user(
                 first_name, last_name, email, password, id_enum_role, deleted
) VALUES ('Vlad', 'Zinko', 'zinko1996@yandex.ru', '12345678', 1, false),
         ('Max', 'Holloway', 'holloway@gmail.ru', md5(random()::text)::char(10), 1, false),
         ('Dustin', 'Poirier', 'poirier@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Justin', 'Gaethje', 'gaethje@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Charles', 'Oliveira', 'oliveira@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Arman', 'Tsarukyan', 'tsarukyan@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Mateusz', 'Gamrot', 'gamrot@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Beneil', 'Dariush', 'dariush@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Michael', 'Chandler', 'chandler@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Rafael', 'Fiziev', 'fisiev@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Renato', 'Moicano', 'moicano@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Dan', 'Hooker', 'hooker@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Jalin', 'Turner', 'turner@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Benoit', 'Saint Denis', 'benoit@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Rafael', 'Dos Anjos', 'rafael@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Bobby', 'Green', 'green@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Ilia', 'Topuria', 'topuria@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Brian', 'Ortega', 'ortega@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Yair', 'Rodriguez', 'rodriguez@gmail.ru', md5(random()::text)::char(10), 2, false),
         ('Arnold', 'Alen', 'alen@gmail.ru', md5(random()::text)::char(10), 2, false);

INSERT INTO public.enum_role (id, role) VALUES (1, 'ADMIN'),
                                               (2, 'CUSTOMER'),
                                               (3, 'MANAGER');