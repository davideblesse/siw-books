// src/main/java/it/uniroma3/siwbooks/controller/admin/AdminBookController.java
package it.uniroma3.siwbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import it.uniroma3.siwbooks.model.Book;
import it.uniroma3.siwbooks.service.AuthorService;
import it.uniroma3.siwbooks.service.BookService;

@Controller
public class AdminAuthorController {

    @Autowired 
    private BookService bookService;

    @Autowired 
    private AuthorService authorService;

    @PostMapping("/admin/authors/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        authorService.deleteById(id);
        return "redirect:/admin/authors";
    }
}
