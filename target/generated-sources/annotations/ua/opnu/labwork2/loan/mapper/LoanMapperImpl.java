package ua.opnu.labwork2.loan.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ua.opnu.labwork2.loan.dto.LoanCreateRequest;
import ua.opnu.labwork2.loan.dto.LoanResponse;
import ua.opnu.labwork2.loan.persistence.Loan;
import ua.opnu.labwork2.loan.persistence.LoanStatus;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-08T21:10:22+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class LoanMapperImpl implements LoanMapper {

    @Override
    public LoanResponse toDTO(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        Long readerId = null;
        Long bookId = null;
        Long id = null;
        LocalDate loanDate = null;
        LocalDate dueDate = null;
        LocalDate returnDate = null;
        LoanStatus status = null;

        readerId = mapReaderToId( loan.getReader() );
        bookId = mapBookToId( loan.getBook() );
        id = loan.getId();
        loanDate = loan.getLoanDate();
        dueDate = loan.getDueDate();
        returnDate = loan.getReturnDate();
        status = loan.getStatus();

        LoanResponse loanResponse = new LoanResponse( id, loanDate, dueDate, returnDate, status, readerId, bookId );

        return loanResponse;
    }

    @Override
    public List<LoanResponse> toDTOList(List<Loan> loans) {
        if ( loans == null ) {
            return null;
        }

        List<LoanResponse> list = new ArrayList<LoanResponse>( loans.size() );
        for ( Loan loan : loans ) {
            list.add( toDTO( loan ) );
        }

        return list;
    }

    @Override
    public Loan toEntity(LoanCreateRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Loan loan = new Loan();

        loan.setLoanDate( dto.loanDate() );
        loan.setDueDate( dto.dueDate() );
        loan.setStatus( dto.status() );

        return loan;
    }
}
