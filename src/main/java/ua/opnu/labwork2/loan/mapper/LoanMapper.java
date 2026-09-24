package ua.opnu.labwork2.loan.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.opnu.labwork2.book.persistence.Book;
import ua.opnu.labwork2.loan.dto.LoanCreateRequest;
import ua.opnu.labwork2.loan.dto.LoanResponse;
import ua.opnu.labwork2.loan.persistence.Loan;
import ua.opnu.labwork2.reader.persistence.Reader;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(source = "reader", target = "readerId")
    @Mapping(source = "book", target = "bookId")
    LoanResponse toDTO(Loan loan);

    List<LoanResponse> toDTOList(List<Loan> loans);

    @Mapping(target = "reader", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "returnDate", ignore = true)
    Loan toEntity(LoanCreateRequest dto);

    default Long mapReaderToId(Reader reader) {
        return (reader != null) ? reader.getId() : null;
    }

    default Long mapBookToId(Book book) {
        return (book != null) ? book.getId() : null;
    }
}