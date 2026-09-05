package id_ocr.repositories;

import id_ocr.entities.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageData, Long> {
}
