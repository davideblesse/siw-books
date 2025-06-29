package it.uniroma3.siwbooks.controller;


import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import it.uniroma3.siwbooks.model.Book;

import it.uniroma3.siwbooks.model.Review;
import it.uniroma3.siwbooks.model.User;
import it.uniroma3.siwbooks.service.*;

@Controller
public class ReviewController {

    @Autowired 
    private ReviewService reviewService;

    @Autowired 
    private CredentialsService credentialsService;

    @Autowired 
    private UserService userService;

    @Autowired 
    private BookService bookService;

    private boolean verifyId(Long id1, Long id2){
        return Objects.equals(id1, id2);
    }


    @PostMapping("/user/{userId}/books/{bookId}/review")
    public String addReview(@PathVariable("userId") Long userId,
                            @PathVariable("bookId")  Long bookId,
                            @RequestParam("title") String title,
                            @RequestParam("text") String text,
                            @RequestParam("mark") int mark,
                            Model model) {
        Book book = this.bookService.findById(bookId);
        User user = this.userService.getCurrentUser();

        if (book == null || user == null || !verifyId(userId, user.getId())){
            return "redirect:/login";
        }

        boolean hasReview = reviewService.existsByUserAndBook(user, book);

        model.addAttribute("hasReview", hasReview);

        this.reviewService.save(new Review(title, mark, text, user, book));
        return "redirect:/user/books/" + bookId;
    }

    @PostMapping("/admin/{userId}/books/{bookId}/review")
    public String addAdminReview(@PathVariable("userId") Long userId,
                            @PathVariable("bookId")  Long bookId,
                            @RequestParam("title") String title,
                            @RequestParam("text") String text,
                            @RequestParam("mark") int mark,
                            Model model) {
        Book book = this.bookService.findById(bookId);
        User user = this.userService.getCurrentUser();

        if (book == null || user == null || !verifyId(userId, user.getId())){
            return "redirect:/login";
        }

        boolean hasReview = reviewService.existsByUserAndBook(user, book);

        model.addAttribute("hasReview", hasReview);

        this.reviewService.save(new Review(title, mark, text, user, book));
        return "redirect:/admin/books/" + bookId;
    }

    @PostMapping("/admin/review/{id}/delete")
    public String deleteReview(@PathVariable("id") Long id){
        User user = this.userService.getCurrentUser();
        Review review = reviewService.findById(id);
        Book book = review.getBook();

        if (book == null || user == null){
            return "redirect:/login";
        }

        reviewService.deleteById(id);
        return "redirect:/admin/books/" + book.getId();
    }
}
