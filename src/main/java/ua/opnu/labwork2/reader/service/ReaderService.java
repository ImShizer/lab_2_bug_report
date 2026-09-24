package ua.opnu.labwork2.reader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.exception.ConflictOperationException;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.loan.dto.LoanResponse;
import ua.opnu.labwork2.loan.mapper.LoanMapper;
import ua.opnu.labwork2.loan.persistence.LoanRepository;
import ua.opnu.labwork2.reader.dto.ReaderCreateRequest;
import ua.opnu.labwork2.reader.dto.ReaderResponse;
import ua.opnu.labwork2.reader.dto.ReaderUpdateRequest;
import ua.opnu.labwork2.reader.mapper.ReaderMapper;
import ua.opnu.labwork2.reader.persistence.Reader;
import ua.opnu.labwork2.reader.persistence.ReaderRepository;
import ua.opnu.labwork2.exception.DuplicateResourceException;

import java.util.List;

@Service
public class ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private LoanMapper loanMapper;

    public List<ReaderResponse> getAll() {
        return readerMapper.toDTOList(readerRepository.findAll());
    }

    public ReaderResponse getById(Long id) {
        Reader reader = readerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Читача з ID " + id + " не знайдено"));
        return readerMapper.toDTO(reader);
    }

    public ReaderResponse create(ReaderCreateRequest request) {
        if (readerRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Читач з email " + request.email() + " вже існує в системі");
        }

        Reader reader = readerMapper.toEntity(request);
        return readerMapper.toDTO(readerRepository.save(reader));
    }

    public ReaderResponse update(Long id, ReaderUpdateRequest request) {
        Reader existingReader = readerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Читача з ID " + id + " не знайдено"));

        readerMapper.updateEntityFromDto(request, existingReader);
        return readerMapper.toDTO(readerRepository.save(existingReader));
    }

    public void delete(Long id) {
        if (!readerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Читача з ID " + id + " не знайдено");
        }

        if (!loanRepository.findByReaderId(id).isEmpty()) {
            throw new ConflictOperationException("Неможливо видалити читача, оскільки у нього є історія оренди.");
        }

        readerRepository.deleteById(id);
    }

    public List<ReaderResponse> search(String query) {
        return readerMapper.toDTOList(
                readerRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query)
        );
    }

    public List<LoanResponse> getLoansByReaderId(Long readerId) {
        if (!readerRepository.existsById(readerId)) {
            throw new RuntimeException("Читача з ID " + readerId + " не знайдено");
        }
        return loanMapper.toDTOList(loanRepository.findByReaderId(readerId));
    }
}