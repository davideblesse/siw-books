// src/main/java/it/uniroma3/siwbooks/controller/admin/AdminBookController.java
package it.uniroma3.siwbooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import it.uniroma3.siwbooks.model.Book;
import it.uniroma3.siwbooks.service.BookService;

@Controller
@RequestMapping("/admin/books")
public class AdminBookController {

    @Autowired private BookService bookService;

    /* ---------- FORM NUOVO ------------------------------------------- */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("action", "create");
        return "admin_book_form";
    }

    /* ---------- CREATE ------------------------------------------------ */
    @PostMapping
    public String createBook(@ModelAttribute @Valid Book book,
                             BindingResult binding) {
        if (binding.hasErrors()) return "admin_book_form";
        bookService.save(book);
        return "redirect:/books";
    }

    /* ---------- FORM EDIT -------------------------------------------- */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book b = bookService.findById(id).orElseThrow();
        model.addAttribute("book", b);
        model.addAttribute("action", "edit");
        return "admin_book_form";
    }

    /* ---------- UPDATE ----------------------------------------------- */
    @PostMapping("/{id}/edit")
    public String updateBook(@PathVariable Long id,
                             @ModelAttribute @Valid Book form,
                             BindingResult binding) {
        if (binding.hasErrors()) return "admin_book_form";

        Book b = bookService.findById(id).orElseThrow();
        b.setTitle(form.getTitle());
        b.setPublicationYear(form.getPublicationYear());
        bookService.save(b);
        return "redirect:/books/" + id;
    }
}
