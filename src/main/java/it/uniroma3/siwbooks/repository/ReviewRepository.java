package it.uniroma3.siwbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siwbooks.model.*;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  boolean existsByUserAndBook(User user, Book book);
  Optional<Review> findByUserAndBook(User user, Book book);
}
