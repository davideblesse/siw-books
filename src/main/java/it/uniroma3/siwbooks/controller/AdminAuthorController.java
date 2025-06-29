// src/main/java/it/uniroma3/siwbooks/controller/AdminAuthorController.java
package it.uniroma3.siwbooks.controller;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwbooks.constant.Nationality;
import it.uniroma3.siwbooks.model.Author;
import it.uniroma3.siwbooks.model.ImageEntity;
import it.uniroma3.siwbooks.service.AuthorService;
import it.uniroma3.siwbooks.service.UserService;

@Controller
@RequestMapping("/admin/authors")
public class AdminAuthorController {

    @Autowired private AuthorService authorService;
    @Autowired private UserService   userService;

    @Value("${upload.dir}") private String uploadDir;   // es. src/main/resources/static/images

    /* ---------- FORM CREAZIONE -------------------------------------- */
    @GetMapping("/new")
    public String showCreate(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("author", new Author());
        model.addAttribute("allNations", Nationality.values());
        model.addAttribute("user", userService.getCurrentUser());
        return "admin/admin_author_form";
    }

    /* ---------- CREAZIONE ------------------------------------------- */
    @PostMapping
    public String createAuthor(@ModelAttribute Author author,
                               @RequestParam(name="photoFile", required=false) MultipartFile photoFile)
                               throws IOException {

        if (photoFile != null && !photoFile.isEmpty()) {
            ImageEntity photo = storeFileAndBuildEntity(photoFile);
            author.setPhoto(photo);
        }
        authorService.save(author);
        return "redirect:/admin/authors";
    }

    /* ---------- FORM MODIFICA --------------------------------------- */
    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) {
        Author a = authorService.findById(id);
        if (a == null) return "error";

        model.addAttribute("action", "edit");
        model.addAttribute("author", a);
        model.addAttribute("allNations", Nationality.values());
        model.addAttribute("user", userService.getCurrentUser());
        return "admin/admin_author_form";
    }

    /* ---------- UPDATE ---------------------------------------------- */
    @PostMapping("/{id}/edit")
    public String updateAuthor(@PathVariable Long id,
                               @ModelAttribute Author author,
                               @RequestParam(name="photoFile", required=false) MultipartFile photoFile)
                               throws IOException {

        Author existing = authorService.findById(id);
        if (existing == null) return "error";

        existing.setName(author.getName());
        existing.setSurname(author.getSurname());
        existing.setBirthDate(author.getBirthDate());
        existing.setDeathDate(author.getDeathDate());
        existing.setNationality(author.getNationality());

        if (photoFile != null && !photoFile.isEmpty()) {
            ImageEntity img = storeFileAndBuildEntity(photoFile);
            existing.setPhoto(img);
        }

        authorService.save(existing);
        return "redirect:/admin/authors";
    }

    /* ---------- DELETE ---------------------------------------------- */
    @PostMapping("/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteById(id);
        return "redirect:/admin/authors";
    }

    /* ---------- helper: salva file e crea ImageEntity ---------------- */
    private ImageEntity storeFileAndBuildEntity(MultipartFile file) throws IOException {
        String filename = Path.of(file.getOriginalFilename()).getFileName().toString();

        // cartella fisica dove copiare i file (uguale a prima)
        Path dest = Paths.get(uploadDir).resolve(filename);
        Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

        // salva SOLO il filename nell'entità
        ImageEntity ie = new ImageEntity();
        ie.setName(filename);
        return ie;
    }
}
