package it.uniroma3.siwbooks.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwbooks.model.Author;
import it.uniroma3.siwbooks.repository.AuthorRepository;

@Service
public class AuthorService {
    @Autowired
    public AuthorRepository authorRepository;
    
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public void save(Author author){
        authorRepository.save(author);
    }

    public void deleteById(Long id){
        authorRepository.deleteById(id);
    }
}