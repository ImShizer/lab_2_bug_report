package ua.opnu.labwork2.loan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.book.persistence.Book;
import ua.opnu.labwork2.book.persistence.BookRepository;
import ua.opnu.labwork2.exception.BadRequestException;
import ua.opnu.labwork2.exception.ConflictOperationException;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.loan.dto.LoanCreateRequest;
import ua.opnu.labwork2.loan.dto.LoanResponse;
import ua.opnu.labwork2.loan.dto.LoanReturnRequest; // Твій новий DTO!
import ua.opnu.labwork2.loan.mapper.LoanMapper;
import ua.opnu.labwork2.loan.persistence.Loan;
import ua.opnu.labwork2.loan.persistence.LoanRepository;
import ua.opnu.labwork2.loan.persistence.LoanStatus;
import ua.opnu.labwork2.reader.persistence.Reader;
import ua.opnu.labwork2.reader.persistence.ReaderRepository;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private LoanMapper mapper;

    public List<LoanResponse> getAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public LoanResponse getById(Long id) {
        Loan loan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запис про оренду з ID " + id + " не знайдено"));
        return mapper.toDTO(loan);
    }

    public LoanResponse create(LoanCreateRequest request) {
        if (request.dueDate().isBefore(request.loanDate())) {
            throw new BadRequestException("Дата повернення не може бути раніше дати видачі");
        }

        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new ResourceNotFoundException("Книгу з ID " + request.bookId() + " не знайдено"));
        Reader reader = readerRepository.findById(request.readerId())
                .orElseThrow(() -> new ResourceNotFoundException("Читача з ID " + request.readerId() + " не знайдено"));

        if (repository.existsByBookIdAndStatus(request.bookId(), LoanStatus.ACTIVE)) {
            throw new ConflictOperationException("Ця книга вже видана іншому читачу і ще не була повернута");
        }

        Loan loan = mapper.toEntity(request);
        loan.setBook(book);
        loan.setReader(reader);

        return mapper.toDTO(repository.save(loan));
    }

    public LoanResponse markReturned(Long id, LoanReturnRequest request) {
        Loan loan = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Запис про оренду з ID " + id + " не знайдено"));

        if (request.returnDate().isBefore(loan.getLoanDate())) {
            throw new BadRequestException("Дата фактичного повернення не може бути раніше дати видачі книги");
        }

        loan.setStatus(request.status());
        loan.setReturnDate(request.returnDate());

        return mapper.toDTO(repository.save(loan));
    }

    public List<LoanResponse> getActiveLoans() {
        return mapper.toDTOList(repository.findByStatus(LoanStatus.ACTIVE));
    }

    public List<LoanResponse> getOverdueLoans() {
        return mapper.toDTOList(repository.findByStatus(LoanStatus.OVERDUE));
    }
}