package it.uniroma3.siwbooks.model;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
public class ImageEntity {

    public static final String PATH = "/images/";   // prefisso URL

    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String name;            // es. "/images/holmes.jpg"

    /* relazione inversa verso Book */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
    
    @Autowired
    protected ImageEntity() { }                 // JPA


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.book);
        return hash;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Book getBook() {
        return book;
    }
    public void setBook(Book book) {
        this.book = book;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ImageEntity other = (ImageEntity) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.book, other.book);
    }

}

