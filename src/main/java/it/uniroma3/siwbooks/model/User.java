package it.uniroma3.siwbooks.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
@SequenceGenerator(
  name = "users_seq_gen",      // un nome a tua scelta
  sequenceName = "users_seq",  // la sequenza che hai creato in import.sql
  allocationSize = 50          // deve corrispondere all'INCREMENT BY di users_seq
)
public class User {

    @Id
    @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "users_seq_gen"
    )
    private Long id;

    @NotBlank 
    @Size(min=4, max=10)
    private String nome;

    @NotBlank 
    @Size(min=4, max=10)
    private String cognome;

    @Email(message="Inserire un indirizzo valido")
    @NotBlank
    private String email;

    @OneToMany(mappedBy="user", cascade=CascadeType.ALL)
    private List<Review> recensioni;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Review> getRecensioni() {
        return recensioni;
    }

    public void setRecensioni(List<Review> recensioni) {
        this.recensioni = recensioni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
