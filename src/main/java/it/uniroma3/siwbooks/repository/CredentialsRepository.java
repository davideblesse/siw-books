package it.uniroma3.siwbooks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.model.User;

// CredentialsRepository.java
public interface CredentialsRepository extends JpaRepository<Credentials, Long> {

    Optional<Credentials> findByUsername(String username);
    boolean existsByUsername(String username);
}


