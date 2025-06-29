package it.uniroma3.siwbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwbooks.model.User;
import it.uniroma3.siwbooks.repository.UserRepository;
import jakarta.validation.Valid;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
	private CredentialsService credentialsService;

    public User getCurrentUser(){
        return credentialsService.getCurrentUser();
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void save(@Valid User user){
        userRepository.save(user);
    }
}