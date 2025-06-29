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

    
    @Autowired
    protected ImageEntity() { }                 // JPA


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


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageEntity other = (ImageEntity) obj;
		return Objects.equals(name, other.name);
	}

}

