package it.uniroma3.siwbooks.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import it.uniroma3.siwbooks.model.ImageEntity;
import it.uniroma3.siwbooks.repository.ImageEntityRepository;
import jakarta.transaction.Transactional;

import static it.uniroma3.siwbooks.model.ImageEntity.PATH;

@Service
public class ImageEntityService {
    
	@Autowired
    private ImageEntityRepository imageEntityRepository;
	
    @Value("${upload.dir}")
    private String uploadDir;

    public ImageEntity getImage(Long id) {
        return imageEntityRepository.findById(id).orElse(null);
    }
}
