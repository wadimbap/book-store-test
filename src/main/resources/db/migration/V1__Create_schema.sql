CREATE TABLE IF NOT EXISTS author
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS book
(
    id    SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    isbn  VARCHAR(13) UNIQUE
);

CREATE TABLE IF NOT EXISTS book_author
(
    book_id   BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES book (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES author (id) ON DELETE CASCADE
);
