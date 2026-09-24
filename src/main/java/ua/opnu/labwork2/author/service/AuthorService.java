package ua.opnu.labwork2.author.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.author.dto.AuthorCreateRequest;
import ua.opnu.labwork2.author.dto.AuthorResponse;
import ua.opnu.labwork2.author.dto.AuthorUpdateRequest;
import ua.opnu.labwork2.author.mapper.AuthorMapper;
import ua.opnu.labwork2.author.persistence.Author;
import ua.opnu.labwork2.author.persistence.AuthorRepository;
import ua.opnu.labwork2.book.persistence.BookRepository;
import ua.opnu.labwork2.exception.ConflictOperationException;
import ua.opnu.labwork2.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private AuthorMapper mapper;

    public List<AuthorResponse> getAll() {
        List<Author> authors = repository.findAll();
        return mapper.toDTOList(authors);
    }

    public AuthorResponse getById(Long id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автора з ID " + id + " не знайдено"));
        return mapper.toDTO(author);
    }

    public AuthorResponse create(AuthorCreateRequest request) {
        Author author = mapper.toEntity(request);
        Author savedAuthor = repository.save(author);
        return mapper.toDTO(savedAuthor);
    }

    public AuthorResponse update(Long id, AuthorUpdateRequest request) {
        Author existingAuthor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Автора з ID " + id + " не знайдено"));

        mapper.updateEntityFromDto(request, existingAuthor);
        Author updatedAuthor = repository.save(existingAuthor);
        return mapper.toDTO(updatedAuthor);
    }

    @Autowired
    private BookRepository bookRepository;

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Автора з ID " + id + " не знайдено");
        }

        if (!bookRepository.findByAuthorId(id).isEmpty()) {
            throw new ConflictOperationException("Неможливо видалити автора, оскільки у нього є зареєстровані книги.");
        }

        repository.deleteById(id);
    }
}