package it.uniroma3.siwbooks.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siwbooks.model.*;
import it.uniroma3.siwbooks.repository.*;

@Service
public class ReviewService {

    @Autowired private ReviewRepository reviewRepo;
    @Autowired private BookRepository   bookRepo;
    @Autowired private UserRepository   userRepo;


    public void save(Review review) {
        if (reviewRepo.existsByUserAndBook(review.getUser(), review.getBook())) {
        throw new IllegalStateException("Hai già recensito questo libro");
    }
       reviewRepo.save(review);
    }

    public boolean existsByUserAndBook(User user, Book book){
        return reviewRepo.existsByUserAndBook(user, book);
    }

    public Review findById(Long id){
        return reviewRepo.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        reviewRepo.deleteById(id);
    }

    public Review findByUserAndBook(User user, Book book){
         return reviewRepo.findByUserAndBook(user, book).orElse(null);
    }
}
