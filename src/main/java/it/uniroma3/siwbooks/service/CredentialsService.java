// src/main/java/it/uniroma3/siwbooks/service/CredentialsService.java
package it.uniroma3.siwbooks.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.repository.CredentialsRepository;

@Service
public class CredentialsService implements UserDetailsService {

    public static final String ADMIN_ROLE   = Credentials.ADMIN_ROLE;
    public static final String DEFAULT_ROLE = Credentials.DEFAULT_ROLE;

    @Autowired
    private CredentialsRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    /** Per recuperare l’utente in BookController */
    public Optional<Credentials> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    @Transactional
    public Credentials save(Credentials c) {
        c.setPassword(encoder.encode(c.getPassword()));
        return repo.save(c);
    }

    public boolean existsByUsername(String u) {
        return repo.existsByUsername(u);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Credentials c = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        return User.withUsername(c.getUsername())
                   .password(c.getPassword())
                   .roles(c.getRole())
                   .build();
    }
}
