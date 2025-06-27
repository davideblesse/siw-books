package it.uniroma3.siwbooks.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import it.uniroma3.siwbooks.model.Review;
import it.uniroma3.siwbooks.service.*;

@Controller
@RequestMapping("/books/{bookId}/reviews")
public class ReviewController {

    @Autowired private ReviewService      reviewService;
    @Autowired private CredentialsService credentialsService;

    /* ---------- CREA --------------------------------------------------- */

    @PostMapping
    public String addReview(@PathVariable Long bookId,
                            @RequestParam  Long userId,
                            @ModelAttribute("newReview") @Valid Review newReview,
                            BindingResult binding,
                            RedirectAttributes redirectAttrs) {

        if (binding.hasErrors()) {
            redirectAttrs.addFlashAttribute("reviewError", "Campi non validi");
            return "redirect:/books/" + bookId;
        }

        try {
            reviewService.addReview(bookId, userId, newReview);
        } catch (IllegalStateException e) {
            redirectAttrs.addFlashAttribute("reviewError", e.getMessage());
        }
        return "redirect:/books/" + bookId;
    }

    /* ---------- MODIFICA (form) ---------------------------------------- */

    @GetMapping("/{reviewId}/edit")
    public String showEditForm(@PathVariable Long bookId,
                               @PathVariable Long reviewId,
                               Principal principal,
                               Model model) {

        Long currentUserId = credentialsService
                .findByUsername(principal.getName()).orElseThrow()
                .getUser().getId();

        Review rev = reviewService.getById(reviewId);
        if (!rev.getUser().getId().equals(currentUserId))
            throw new SecurityException("Operazione non consentita");

        model.addAttribute("bookId",    bookId);
        model.addAttribute("editReview", rev);
        return "review_edit";
    }

    /* ---------- MODIFICA (submit) -------------------------------------- */

    @PostMapping("/{reviewId}/edit")
    public String updateReview(@PathVariable Long bookId,
                               @PathVariable Long reviewId,
                               Principal principal,
                               @ModelAttribute("editReview") @Valid Review editReview,
                               BindingResult binding,
                               RedirectAttributes redirectAttrs) {

        Long currentUserId = credentialsService
                .findByUsername(principal.getName()).orElseThrow()
                .getUser().getId();

        if (binding.hasErrors()) return "review_edit";

        try {
            reviewService.updateReview(reviewId, currentUserId, editReview);
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("reviewError", e.getMessage());
        }
        return "redirect:/books/" + bookId;
    }

    /* ---------- ELIMINA ------------------------------------------------ */

    @PostMapping("/{reviewId}/delete")
    public String deleteReview(@PathVariable Long bookId,
                               @PathVariable Long reviewId,
                               @RequestParam  Long userId,
                               RedirectAttributes redirectAttrs) {

        try {
            reviewService.deleteReview(reviewId, userId);
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("reviewError", e.getMessage());
        }
        return "redirect:/books/" + bookId;
    }
}
