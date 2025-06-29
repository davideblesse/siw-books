package it.uniroma3.siwbooks.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class ImageEntity {

    public static final String PATH = "/images/";   // prefisso URL

    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String name;            // es. "holmes.jpg"

    // JPA richiede un costruttore senza argomenti
    public ImageEntity() { }

    public ImageEntity(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Restituisce il path completo per il browser,
     * es. "/images/holmes.jpg"
     */
    public String getName() {
        return PATH + this.name;
    }

    /**
     * Salva solo il nome del file, senza prefisso
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ImageEntity)) return false;
        ImageEntity other = (ImageEntity) obj;
        return Objects.equals(this.name, other.name);
    }
}
