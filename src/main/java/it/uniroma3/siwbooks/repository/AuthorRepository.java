package it.uniroma3.siwbooks.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import it.uniroma3.siwbooks.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{

}
