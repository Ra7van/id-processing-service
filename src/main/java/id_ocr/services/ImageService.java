package id_ocr.services;

import id_ocr.entities.IdentityCard;
import id_ocr.entities.ImageData;
import id_ocr.repositories.IdentityCardRepository;
import id_ocr.repositories.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ImageService {

    private final IdentityCardRepository identityCardRepository;
    private final ImageRepository imageDataRepository;

    public ImageService(
            IdentityCardRepository identityCardRepository,
            ImageRepository imageDataRepository
    ) {
        this.identityCardRepository = identityCardRepository;
        this.imageDataRepository = imageDataRepository;
    }

    public ImageData getImageByUid(String uid) {
        IdentityCard identityCard = identityCardRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException("Identity card not found"));

        return imageDataRepository.findById(identityCard.getId())
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }

    @Transactional
    public void saveIdentityCardWithImage(IdentityCard identityCard, MultipartFile file) {
        try {
            IdentityCard savedCard = identityCardRepository.save(identityCard);

            ImageData imageData = new ImageData();
            imageData.setIdentityCard(savedCard);
            imageData.setImageData(file.getBytes());
            imageData.setContentType(file.getContentType());
            imageData.setFileName(file.getOriginalFilename());
            imageData.setFileSize(file.getSize());
            imageData.setCreatedAt(LocalDateTime.now());

            imageDataRepository.save(imageData);

        } catch (IOException e) {
            throw new RuntimeException("Could not save image", e);
        }
    }
}