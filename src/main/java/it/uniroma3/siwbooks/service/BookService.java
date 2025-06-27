// src/main/java/it/uniroma3/siwbooks/service/BookService.java
package it.uniroma3.siwbooks.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwbooks.model.Book;
import it.uniroma3.siwbooks.repository.BookRepository;

@Service
public class BookService {

    @Autowired private BookRepository repo;

    public List<Book> findAll()              { return repo.findAll(); }
    public Optional<Book> findById(Long id)  { return repo.findById(id); }

    /** inserisce o aggiorna */
    public Book save(Book b) { return repo.save(b); }
}
