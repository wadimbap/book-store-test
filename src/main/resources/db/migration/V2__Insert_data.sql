INSERT INTO author (name)
VALUES ('Leo Tolstoy'),
       ('Jane Austen'),
       ('Fyodor Dostoevsky'),
       ('Mark Twain');

INSERT INTO book (title, isbn)
VALUES ('War and Peace', '9780140447934'),
       ('Anna Karenina', '9780199536061'),
       ('Pride and Prejudice', '9780141439518'),
       ('Crime and Punishment', '9780140449136'),
       ('The Brothers Karamazov', '9780374528379'),
       ('Emma', '9780141439587'),
       ('Sense and Sensibility', '9780141439662'),
       ('The Adventures of Tom Sawyer', '9780143107332');

INSERT INTO book_author (book_id, author_id)
VALUES (1, 1), -- War and Peace - Leo Tolstoy
       (2, 1), -- Anna Karenina - Leo Tolstoy
       (3, 2), -- Pride and Prejudice - Jane Austen
       (4, 3), -- Crime and Punishment - Fyodor Dostoevsky
       (5, 3), -- The Brothers Karamazov - Fyodor Dostoevsky
       (6, 2), -- Emma - Jane Austen
       (7, 2), -- Sense and Sensibility - Jane Austen
       (8, 4); -- The Adventures of Tom Sawyer - Mark Twain
