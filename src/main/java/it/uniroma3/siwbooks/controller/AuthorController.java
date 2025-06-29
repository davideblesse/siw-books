package it.uniroma3.siwbooks.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwbooks.model.Author;
import it.uniroma3.siwbooks.model.User;
import it.uniroma3.siwbooks.service.AuthorService;
import it.uniroma3.siwbooks.service.UserService;

@Controller
public class AuthorController {

    @Autowired
    public AuthorService authorService;

    @Autowired
    public UserService userService;

    @GetMapping("/authors")
    public String listAuthors(Model model){
        model.addAttribute("authors", this.authorService.findAll());
        return "authors";
    }

    @GetMapping("/authors/{id}")
    public String viewAuthor(@PathVariable Long id ,Model model){
        Author author = authorService.findById(id);

        if(author==null){
            return "error";
        }
        model.addAttribute("author", author);
        return "author";
    }

    @GetMapping("/user/authors")
    public String listUserAuthors(Model model){
        User user = userService.getCurrentUser();
        if(user == null){
            return "redirect:/login";
        } 
        model.addAttribute("user", user);
        model.addAttribute("authors", this.authorService.findAll());
        return "user/authors";
    }

    @GetMapping("/user/authors/{id}")
    public String viewUserAuthor(@PathVariable Long id ,Model model){
        Author author = authorService.findById(id);
        User user = userService.getCurrentUser();

        if(author==null){
            return "error";
        }
        if(user == null){
            return "redirect:/login";
        } 
        model.addAttribute("user", user);
        model.addAttribute("author", author);
        return "user/author";
    }

    @GetMapping("/admin/authors")
    public String listAdminAuthors(Model model){
        User user = userService.getCurrentUser();
        if(user == null){
            return "redirect:/login";
        } 
        model.addAttribute("user", user);
        model.addAttribute("authors", this.authorService.findAll());
        return "admin/authors";
    }

    @GetMapping("/admin/authors/{id}")
    public String viewAdminAuthor(@PathVariable Long id ,Model model){
        Author author = authorService.findById(id);
        User user = userService.getCurrentUser();

        if(author==null){
            return "error";
        }
        if(user == null){
            return "redirect:/login";
        } 
        model.addAttribute("user", user);
        model.addAttribute("author", author);
        return "admin/author";
    }
}