
package it.uniroma3.siwbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siwbooks.model.ImageEntity;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Long> { }
