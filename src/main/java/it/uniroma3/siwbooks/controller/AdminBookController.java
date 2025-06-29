// src/main/java/it/uniroma3/siwbooks/controller/AdminBookController.java
package it.uniroma3.siwbooks.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwbooks.model.Book;
import it.uniroma3.siwbooks.model.ImageEntity;
import it.uniroma3.siwbooks.service.AuthorService;
import it.uniroma3.siwbooks.service.BookService;
import it.uniroma3.siwbooks.service.ImageEntityService;
import it.uniroma3.siwbooks.service.UserService;

@Controller
@RequestMapping("/admin/books")
public class AdminBookController {

    @Autowired private BookService bookService;
    @Autowired private AuthorService authorService;
    @Autowired private ImageEntityService imageService;
    @Autowired private UserService userService;
    @Value("${upload.dir}") private String uploadDir;

    /* ---------- FORM CREAZIONE ------------------------------------------------ */
    @GetMapping("/new")
    public String showCreate(Model m) {
        m.addAttribute("action", "create");
        m.addAttribute("book", new Book());                    // <— mancava
        m.addAttribute("allAuthors", authorService.findAll());

        // serve per la navbar
        m.addAttribute("user", userService.getCurrentUser());  // <— mancava
        return "admin/admin_book_form";
    }

    /* ---------- CREAZIONE ----------------------------------------------------- */
    @PostMapping
    public String createBook(@RequestParam String title,
                             @RequestParam Integer publicationYear,
                             @RequestParam(name="authors", required=false) List<Long> authorIds,
                             @RequestParam(name="images", required=false) MultipartFile[] images)
            throws IOException {

        Book book = new Book();
        book.setTitle(title);
        book.setPublicationYear(publicationYear);

        // 1. autori
        if (authorIds != null) {
            authorIds.stream()
                     .map(authorService::findById)
                     .filter(Objects::nonNull)
                     .forEach(book.getAuthors()::add);
        }

        // 2. immagini
        if (images != null) {
            for (MultipartFile mf : images) {
                if (!mf.isEmpty()) {
                    ImageEntity img = storeFileAndBuildEntity(mf);
                    book.getImages().add(img);
                }
            }
        }

        bookService.save(book);
        return "redirect:/admin/books";
    }

    /* ---------- FORM MODIFICA ------------------------------------------------- */
    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable Long id, Model m) {
        Book b = bookService.findById(id);
        if (b == null) return "error";

        m.addAttribute("action", "edit");
        m.addAttribute("book", b);                             // già c’era
        m.addAttribute("allAuthors", authorService.findAll());

        // serve per la navbar
        m.addAttribute("user", userService.getCurrentUser());  // <— aggiungi
        return "admin/admin_book_form";
    }

    /* ---------- UPDATE -------------------------------------------------------- */
    @PostMapping("/{id}/edit")
    public String updateBook(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam Integer publicationYear,
                             @RequestParam(name="authors", required=false) List<Long> authorIds,
                             @RequestParam(name="images", required=false) MultipartFile[] images)
            throws IOException {

        Book book = bookService.findById(id);
        if (book == null) return "error";

        book.setTitle(title);
        book.setPublicationYear(publicationYear);

        // ricalcola autori
        book.getAuthors().clear();
        if (authorIds != null) {
            authorIds.stream()
                     .map(authorService::findById)
                     .filter(Objects::nonNull)
                     .forEach(book.getAuthors()::add);
        }

        // aggiungi eventuali nuove immagini
        if (images != null) {
            for (MultipartFile mf : images) {
                if (!mf.isEmpty()) {
                    ImageEntity img = storeFileAndBuildEntity(mf);
                    book.getImages().add(img);
                }
            }
        }

        bookService.save(book);
        return "redirect:/admin/books";
    }

    /* ---------- DELETE (già presente) ---------------------------------------- */
    @PostMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/admin/books";
    }

    /* ---------- helper file → ImageEntity ------------------------------------ */
    private ImageEntity storeFileAndBuildEntity(MultipartFile file) throws IOException {
        String filename = Path.of(file.getOriginalFilename()).getFileName().toString();

        // cartella fisica dove copiare i file (uguale a prima)
        Path dest = Paths.get(uploadDir).resolve(filename);
        Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

        // salva SOLO il filename nell'entità
        ImageEntity ie = new ImageEntity();
        ie.setName(filename);               // <— senza UUID, senza "/images/"
        return ie;
    }
}
