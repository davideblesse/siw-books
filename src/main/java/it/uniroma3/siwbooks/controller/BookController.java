package it.uniroma3.siwbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwbooks.model.Book;
import it.uniroma3.siwbooks.model.Review;
import it.uniroma3.siwbooks.model.User;
import it.uniroma3.siwbooks.service.BookService;
import it.uniroma3.siwbooks.service.ReviewService;
import it.uniroma3.siwbooks.service.UserService;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReviewService reviewService;


    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping("/books/{id}")
    public String viewBook(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);
        if(book == null){
            return "error";
        }
        model.addAttribute("book", book);

        return "book";
    }

    @GetMapping("/user/books")
    public String listUserBooks(Model model) {
        User user = userService.getCurrentUser();
        if (user == null){
            return "redirect:/login";
        }

        model.addAttribute("books", bookService.findAll());
        model.addAttribute("user", user);
        return "user/books";
    }

    @GetMapping("/user/books/{id}")
    public String viewUserBook(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);
        User user = userService.getCurrentUser();

        if(book == null){
            return "error.html";
        }
        if (user == null){
            return "redirect:/login";
        }
        boolean hasReview = reviewService.existsByUserAndBook(user, book);

        Review myReviewEntity = null;
        if (hasReview) {
            myReviewEntity = reviewService.findByUserAndBook(user, book);
        }

        model.addAttribute("review", new Review());
        
        model.addAttribute("myReview", myReviewEntity);
        model.addAttribute("hasReview", hasReview);
        model.addAttribute("book", book);
        model.addAttribute("user", user);
        return "user/book";
    }

    @GetMapping("/admin/books")
    public String listAdminBooks(Model model) {
        User user = userService.getCurrentUser();
        if (user == null){
            return "redirect:/login";
        }

        model.addAttribute("books", bookService.findAll());
        model.addAttribute("user", user);
        return "admin/books";
    }

    @GetMapping("/admin/books/{id}")
    public String viewAdminBook(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);
        User user = userService.getCurrentUser();

        if(book == null){
            return "error.html";
        }
        if (user == null){
            return "redirect:/login";
        }
        boolean hasReview = reviewService.existsByUserAndBook(user, book);
        model.addAttribute("hasReview", hasReview);
        model.addAttribute("book", book);
        model.addAttribute("user", user);
        return "admin/book";
    }
}

