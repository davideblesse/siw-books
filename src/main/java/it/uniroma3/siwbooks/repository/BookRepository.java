package it.uniroma3.siwbooks.repository;

import it.uniroma3.siwbooks.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
