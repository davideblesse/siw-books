package it.uniroma3.siwbooks.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siwbooks.model.*;
import it.uniroma3.siwbooks.service.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final CredentialsService credentialsService;
    private final ReviewService reviewService;

    @Autowired
    public BookController(BookService bs,
                          CredentialsService cs,
                          ReviewService rs) {
        this.bookService        = bs;
        this.credentialsService = cs;
        this.reviewService      = rs;
    }

    /** ← new: lista di tutti i libri, resa disponibile su GET /books */
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books";
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id,
                           Model model,
                           Principal principal) {
        return bookService.findById(id)
            .map(book -> {
                model.addAttribute("book", book);
                model.addAttribute("newReview", new Review());

                if (principal != null) {
                    Credentials cred = credentialsService.findByUsername(principal.getName())
                                                         .orElseThrow();
                    Long currentUserId = cred.getUser().getId();
                    model.addAttribute("currentUserId", currentUserId);

                    boolean hasReviewed = reviewService
                        .existsByUserAndBook(cred.getUser(), book);
                    model.addAttribute("canReview", !hasReviewed);
                } else {
                    model.addAttribute("canReview", false);
                }

                return "book";
            })
            .orElse("error");
    }
}
