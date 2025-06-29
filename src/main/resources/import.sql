-- IMMAGINI
INSERT INTO image_entity (id, name) VALUES (1, 'Orgoglio_E_Pregiudizio_1.jpg');
INSERT INTO image_entity (id, name) VALUES (2, 'Orgoglio_E_Pregiudizio_2.jpg');
INSERT INTO image_entity (id, name) VALUES (3, 'Cent_Anni_1.jpg');
INSERT INTO image_entity (id, name) VALUES (4, 'Cent_Anni_2.jpg');
INSERT INTO image_entity (id, name) VALUES (5, 'Ragione_E_Sentimento_1.jpg');
INSERT INTO image_entity (id, name) VALUES (6, 'Ragione_E_Sentimento_2.jpg');
INSERT INTO image_entity (id, name) VALUES (7, 'Amore_Ai_Tempi_1.jpg');
INSERT INTO image_entity (id, name) VALUES (8, 'Amore_Ai_Tempi_2.jpg');
INSERT INTO image_entity (id, name) VALUES (9, 'Jane_Austen_coloured_version.jpg');
INSERT INTO image_entity (id, name) VALUES (10, 'Gabriel_Garcia_Marquez.jpg');

-- AUTORI
INSERT INTO author (id, name, surname, birth_date, death_date, nationality, photo_id) VALUES (1, 'Jane', 'Austen', '1775-12-16', '1817-07-18', 'UK', 9);
INSERT INTO author (id, name, surname, birth_date, death_date, nationality, photo_id) VALUES (2, 'Gabriel', 'García Márquez', '1927-03-06', '2014-04-17', 'COLOMBIA', 10);

-- LIBRI
INSERT INTO book (id, title, publication_year) VALUES (1, 'Orgoglio e Pregiudizio', 1813);
INSERT INTO book (id, title, publication_year) VALUES (2, 'Ragione e Sentimento', 1811);
INSERT INTO book (id, title, publication_year) VALUES (3, 'Cent anni di solitudine', 1967);
INSERT INTO book (id, title, publication_year) VALUES (4, 'L amore ai tempi del colera', 1985);


-- RELAZIONE LIBRI-AUTORI
INSERT INTO book_authors (authors_id, books_id) VALUES (1, 1);
INSERT INTO book_authors (authors_id, books_id) VALUES (1, 2);
INSERT INTO book_authors (authors_id, books_id) VALUES (2, 3);
INSERT INTO book_authors (authors_id, books_id) VALUES (2, 4);

-- RELAZIONE LIBRO-IMMAGINI
INSERT INTO book_images (book_id, images_id) VALUES (1, 1);
INSERT INTO book_images (book_id, images_id) VALUES (1, 2);
INSERT INTO book_images (book_id, images_id) VALUES (2, 3);
INSERT INTO book_images (book_id, images_id) VALUES (2, 4);
INSERT INTO book_images (book_id, images_id) VALUES (3, 5);
INSERT INTO book_images (book_id, images_id) VALUES (3, 6);
INSERT INTO book_images (book_id, images_id) VALUES (4, 7);
INSERT INTO book_images (book_id, images_id) VALUES (4, 8);


-- UTENTI
INSERT INTO users (id, nome, cognome, email) VALUES (1, 'Alice', 'Bianchi', 'alice@example.com');
INSERT INTO users (id, nome, cognome, email) VALUES (2, 'Mario', 'Rossi', 'mario.rossi@uniroma3.it');

-- RECENSIONI
INSERT INTO review (id, title, text, mark, book_id, user_id) VALUES (1, 'Un capolavoro',               'La profondità dei personaggi e il dialogo brillante lo rendono indimenticabile.', 5, 1, 1);
INSERT INTO review (id, title, text, mark, book_id, user_id) VALUES (2, 'Emozionante',                 'Una storia di sentimenti e doveri che cattura il cuore ad ogni pagina.',            4, 2, 2);
INSERT INTO review (id, title, text, mark, book_id, user_id) VALUES (3, 'Avvolgente come un mito',     'La saga della famiglia Buendía è epica, magica e struggente allo stesso tempo.',      5, 3, 1);
INSERT INTO review (id, title, text, mark, book_id, user_id) VALUES (4, 'Storia d’amore intensa',      'La passione e la nostalgia di Florentino e Fermina restano impresse nella mente.',   4, 4, 2);


-- password = admin (BCrypt: cambia se vuoi)
INSERT INTO credentials (id, username, password, role, user_id) VALUES (10, 'admin', '$2a$12$IHPBPKzSiG5TExakOPaLKuGDlpHoElnteQe4kRoc2n3RUAADPb6q2', 'ADMIN', 1);
-- password = admin (BCrypt: cambia se vuoi)
INSERT INTO credentials (id, username, password, role, user_id) VALUES (11, 'user', '$2a$12$UInFQ4nlMZYVIheDPzNrDOwxhRCa.hhKcH9uNmHdIYga0qOLC1MwW', 'DEFAULT', 2);

 
-- (Eventuali CREDENTIALS da preinserire, se ti servono)
-- INSERT INTO credentials (id, username, password, role, user_id) VALUES (1, 'alice', '$2a$10$…', 'DEFAULT', 1);

-- RIPRISTINO DELLE SEQUENZE PER EVITARE DUPLICATI
SELECT setval('author_seq',         (SELECT MAX(id) FROM author));
SELECT setval('book_seq',           (SELECT MAX(id) FROM book));
SELECT setval('image_entity_seq',   (SELECT MAX(id) FROM image_entity));
SELECT setval('users_seq',          (SELECT MAX(id) FROM users));
SELECT setval('review_seq',         (SELECT MAX(id) FROM review));
SELECT setval('credentials_seq', (SELECT MAX(id) FROM credentials));
