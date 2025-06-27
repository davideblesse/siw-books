// src/main/java/it/uniroma3/siwbooks/controller/HomeController.java
package it.uniroma3.siwbooks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // forward root → your list of books
        return "redirect:/books";
    }
}

