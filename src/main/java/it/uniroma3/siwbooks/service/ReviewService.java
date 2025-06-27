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

    /* ------------- CREA ------------------------------------------------ */

    @Transactional
    public void addReview(Long bookId, Long userId, Review review) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new NoSuchElementException("Book not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (existsByUserAndBook(user, book))
            throw new IllegalStateException("Hai già recensito questo libro");

        review.setBook(book);
        review.setUser(user);
        reviewRepo.save(review);
    }

    /* ------------- LEGGI ------------------------------------------------ */

    public boolean existsByUserAndBook(User u, Book b) {
        return reviewRepo.existsByUserAndBook(u, b);
    }

    public Review getById(Long id) {
        return reviewRepo.findById(id)
               .orElseThrow(() -> new NoSuchElementException("Review not found"));
    }

    /* ------------- AGGIORNA -------------------------------------------- */

    @Transactional
    public void updateReview(Long reviewId, Long userId, Review form) {
        Review rev = getById(reviewId);

        if (!rev.getUser().getId().equals(userId))
            throw new SecurityException("Operazione non consentita");

        rev.setTitle(form.getTitle());
        rev.setText(form.getText());
        rev.setMark(form.getMark());
        // nessun save esplicito necessario: la tx commit fa il flush
    }

    /* ------------- ELIMINA --------------------------------------------- */

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review rev = getById(reviewId);

        if (!rev.getUser().getId().equals(userId))
            throw new SecurityException("Operazione non consentita");

        reviewRepo.delete(rev);
    }
}
