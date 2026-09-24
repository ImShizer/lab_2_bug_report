package ua.opnu.labwork2.loan.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByReaderId(Long readerId);

    List<Loan> findByBookId(Long bookId);

    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByReaderIdAndStatus(Long readerId, LoanStatus status);

    boolean existsByBookIdAndStatus(Long bookId, LoanStatus status);
}