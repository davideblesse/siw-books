-- LIBRI
INSERT INTO book (id, title, publication_year) VALUES (1, 'Sword Art Online – Aincrad 1', 2009);
INSERT INTO book (id, title, publication_year) VALUES (2, 'Tutto Sherlock Holmes', 2010);
INSERT INTO book (id, title, publication_year) VALUES (3, 'Sword Art Online – Aincrad 2', 2009);

-- AUTORI
INSERT INTO author (id, name, surname, birth_date, nationality) VALUES (1, 'Reki', 'Kawahara', '1974-08-17', 'JAPAN');
INSERT INTO author (id, name, surname, birth_date, nationality) VALUES (2, 'Arthur', 'Conan Doyle', '1859-05-22', 'UK');

-- RELAZIONE LIBRI-AUTORI
INSERT INTO book_authors (books_id, authors_id) VALUES (1, 1);
INSERT INTO book_authors (books_id, authors_id) VALUES (3, 1);
INSERT INTO book_authors (books_id, authors_id) VALUES (2, 2);

-- IMMAGINI
INSERT INTO image_entity (id, name, book_id) VALUES (1, '/images/Sword_Art_Online_Aincrad_1.jpg', 1);
INSERT INTO image_entity (id, name, book_id) VALUES (2, '/images/Sword_Art_Online_Aincrad_1_1.jpg', 1);
INSERT INTO image_entity (id, name, book_id) VALUES (3, '/images/Sword_Art_Online_Aincrad_2.jpg', 3);
INSERT INTO image_entity (id, name, book_id) VALUES (4, '/images/Tutto_Sherlock_Holmes.jpg', 2);
INSERT INTO image_entity (id, name)           VALUES (5, '/images/Reki_Kawahara.jpg');
INSERT INTO image_entity (id, name)           VALUES (6, '/images/Arthur_Conan_Doyle.jpg');

-- UTENTI
INSERT INTO users (id, nome, cognome, email) VALUES (1, 'Alice', 'Bianchi', 'alice@example.com');
INSERT INTO users (id, nome, cognome, email) VALUES (2, 'Mario', 'Rossi', 'mario.rossi@uniroma3.it');

-- RECENSIONI
INSERT INTO review (id, title, text, mark, book_id, user_id) VALUES (1, 'Bellissimo',          'Mi ha catturato fin dalla prima pagina.',        5, 1, 1);
INSERT INTO review (id, title, text, mark, book_id, user_id) VALUES (2, 'Molto interessante', 'Stile scorrevole, trama avvincente.',            4, 1, 2);
INSERT INTO review (id, title, text, mark, book_id, user_id) VALUES (3, 'Classico imperdibile','Holmes è sempre Holmes!',                       5, 2, 1);

-- (Eventuali CREDENTIALS da preinserire, se ti servono)
-- INSERT INTO credentials (id, username, password, role, user_id) VALUES (1, 'alice', '$2a$10$…', 'DEFAULT', 1);

-- RIPRISTINO DELLE SEQUENZE PER EVITARE DUPLICATI
SELECT setval('author_seq',         (SELECT MAX(id) FROM author));
SELECT setval('book_seq',           (SELECT MAX(id) FROM book));
SELECT setval('image_entity_seq',   (SELECT MAX(id) FROM image_entity));
SELECT setval('users_seq',          (SELECT MAX(id) FROM users));
SELECT setval('review_seq',         (SELECT MAX(id) FROM review));
-- SELECT setval('credentials_seq', (SELECT MAX(id) FROM credentials));

-- password = admin (BCrypt: cambia se vuoi)
INSERT INTO credentials (id, username, password, role, user_id) VALUES (10, 'admin', '$2a$12$IHPBPKzSiG5TExakOPaLKuGDlpHoElnteQe4kRoc2n3RUAADPb6q2', 'ADMIN', 1);

 