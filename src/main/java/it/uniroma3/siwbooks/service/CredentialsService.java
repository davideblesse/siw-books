// src/main/java/it/uniroma3/siwbooks/service/CredentialsService.java
package it.uniroma3.siwbooks.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static it.uniroma3.siwbooks.model.Credentials.ADMIN_ROLE;
import static it.uniroma3.siwbooks.model.Credentials.DEFAULT_ROLE;

import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.repository.CredentialsRepository;
import jakarta.validation.Valid;

import it.uniroma3.siwbooks.model.User;

@Service
public class CredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** Per recuperare l’utente in BookController */
    public Credentials findByUsername(String username) {
        return credentialsRepository.findByUsername(username).orElse(null);
    }

    public void save(@Valid Credentials credentials) {
        credentials.setRole(DEFAULT_ROLE);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        credentialsRepository.save(credentials);
    }

    public boolean existsByUsername(String u) {
        return credentialsRepository.existsByUsername(u);
    }

    public User getCurrentUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.findByUsername(userDetails.getUsername()).getUser();
    }
}
