package id_ocr.repositories;

import id_ocr.entities.IdentityCard;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface IdentityCardRepository extends JpaRepository<IdentityCard, Long> {
    Optional<IdentityCard> findByUid(String uid);

    Page<IdentityCard> findByCnpContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String cnp,
            String firstName,
            String lastName,
            Pageable pageable
    );
}