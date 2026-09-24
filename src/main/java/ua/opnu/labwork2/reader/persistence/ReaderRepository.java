package ua.opnu.labwork2.reader.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
    List<Reader> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    boolean existsByEmail(String email);
}