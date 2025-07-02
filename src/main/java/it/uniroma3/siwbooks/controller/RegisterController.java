package it.uniroma3.siwbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siwbooks.model.Credentials;
import it.uniroma3.siwbooks.service.CredentialsService;
import jakarta.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("credentials", new Credentials());
        return "register";
    }


    @PostMapping("/register")
    public String registerUser(
        @Valid @ModelAttribute("credentials") Credentials credentials,
        BindingResult binding
    ) {
        if (credentialsService.existsByUsername(credentials.getUsername())) {
            binding.rejectValue("username","error.credentials","Username già esistente");
        }

        if(!credentials.getPassword().equals(credentials.getConfirmPassword())){
            binding.rejectValue("confirmPassword", "error.confirmPassword", "Le due password devono coincidere");
        }

        if (binding.hasErrors()) return "register";

        credentials.setRole(Credentials.DEFAULT_ROLE);
        credentialsService.save(credentials);
        return "redirect:/login?registered";
    }

}

