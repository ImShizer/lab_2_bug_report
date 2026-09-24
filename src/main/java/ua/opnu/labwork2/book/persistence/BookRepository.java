package ua.opnu.labwork2.book.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(Long authorId);
    List<Book> findByCategoriesId(Long categoryId);
    List<Book> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String desc);
    boolean existsByIsbn(String isbn);
}