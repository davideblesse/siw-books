package it.uniroma3.siwbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.model.User;
import it.uniroma3.siwbooks.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private CredentialsService credentialsService;

    /**
     * Mostra la pagina di registrazione.
     */
    @GetMapping("/register")
    public String showRegister(Model model) {
        // crea Credentials E lo “collega” a un nuovo User
        Credentials credentials = new Credentials();
        credentials.setUser(new User());     // ← inizializza il user interno

        model.addAttribute("credentials", credentials);
        return "register";
    }



    /**
     * Processa il form di registrazione.
    */
    @PostMapping("/register")
    public String registerUser(
        @Valid @ModelAttribute("credentials") Credentials credentials,
        BindingResult binding
    ) {
        if (credentialsService.existsByUsername(credentials.getUsername())) {
            binding.rejectValue("username","error.credentials","Username già esistente");
        }
        if (binding.hasErrors()) return "register";

        // role e user già collegati
        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentialsService.save(credentials);
        return "redirect:/login?registered";
    }

}

