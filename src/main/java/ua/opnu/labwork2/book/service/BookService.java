package ua.opnu.labwork2.book.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.author.persistence.Author;
import ua.opnu.labwork2.author.persistence.AuthorRepository;
import ua.opnu.labwork2.book.dto.BookCreateRequest;
import ua.opnu.labwork2.book.dto.BookResponse;
import ua.opnu.labwork2.book.dto.BookUpdateRequest;
import ua.opnu.labwork2.book.mapper.BookMapper;
import ua.opnu.labwork2.book.persistence.Book;
import ua.opnu.labwork2.book.persistence.BookRepository;
import ua.opnu.labwork2.category.persistence.Category;
import ua.opnu.labwork2.category.persistence.CategoryRepository;
import ua.opnu.labwork2.exception.ConflictOperationException;
import ua.opnu.labwork2.exception.DuplicateResourceException;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.loan.persistence.LoanRepository;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookMapper mapper;

    public List<BookResponse> getAll() {
        return mapper.toDTOList(bookRepository.findAll());
    }

    public BookResponse getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книгу з ID " + id + " не знайдено"));
        return mapper.toDTO(book);
    }

    public BookResponse create(BookCreateRequest request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new DuplicateResourceException("Книга з ISBN " + request.isbn() + " вже існує в системі");
        }

        Book book = mapper.toEntity(request);

        Author author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("Автора з ID " + request.authorId() + " не знайдено"));
        book.setAuthor(author);

        return mapper.toDTO(bookRepository.save(book));
    }

    public BookResponse update(Long id, BookUpdateRequest request) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книгу з ID " + id + " не знайдено"));

        mapper.updateEntityFromDto(request, existingBook);

        if (!existingBook.getAuthor().getId().equals(request.authorId())) {
            Author newAuthor = authorRepository.findById(request.authorId())
                    .orElseThrow(() -> new RuntimeException("Автора з ID " + request.authorId() + " не знайдено"));
            existingBook.setAuthor(newAuthor);
        }

        return mapper.toDTO(bookRepository.save(existingBook));
    }

    @Autowired
    private LoanRepository loanRepository;

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Книгу з ID " + id + " не знайдено");
        }

        if (!loanRepository.findByBookId(id).isEmpty()) {
            throw new ConflictOperationException("Неможливо видалити книгу, оскільки з нею пов'язані записи про оренду.");
        }

        bookRepository.deleteById(id);
    }

    public List<BookResponse> getByAuthor(Long authorId) {
        return mapper.toDTOList(bookRepository.findByAuthorId(authorId));
    }

    public List<BookResponse> getByCategory(Long categoryId) {
        return mapper.toDTOList(bookRepository.findByCategoriesId(categoryId));
    }

    public BookResponse addCategoryToBook(Long bookId, Long categoryId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книгу не знайдено"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Категорію не знайдено"));

        if (!book.getCategories().contains(category)) {
            book.getCategories().add(category);
        }

        return mapper.toDTO(bookRepository.save(book));
    }

    public BookResponse removeCategoryFromBook(Long bookId, Long categoryId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книгу не знайдено"));

        book.getCategories().removeIf(c -> c.getId().equals(categoryId));
        return mapper.toDTO(bookRepository.save(book));
    }
}