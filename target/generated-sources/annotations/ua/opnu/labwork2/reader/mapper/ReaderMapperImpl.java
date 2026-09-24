package ua.opnu.labwork2.reader.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ua.opnu.labwork2.reader.dto.ReaderCreateRequest;
import ua.opnu.labwork2.reader.dto.ReaderResponse;
import ua.opnu.labwork2.reader.dto.ReaderUpdateRequest;
import ua.opnu.labwork2.reader.persistence.Reader;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T22:23:40+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class ReaderMapperImpl implements ReaderMapper {

    @Override
    public ReaderResponse toDTO(Reader reader) {
        if ( reader == null ) {
            return null;
        }

        Long id = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        String phone = null;
        LocalDate registrationDate = null;

        id = reader.getId();
        firstName = reader.getFirstName();
        lastName = reader.getLastName();
        email = reader.getEmail();
        phone = reader.getPhone();
        registrationDate = reader.getRegistrationDate();

        ReaderResponse readerResponse = new ReaderResponse( id, firstName, lastName, email, phone, registrationDate );

        return readerResponse;
    }

    @Override
    public List<ReaderResponse> toDTOList(List<Reader> readers) {
        if ( readers == null ) {
            return null;
        }

        List<ReaderResponse> list = new ArrayList<ReaderResponse>( readers.size() );
        for ( Reader reader : readers ) {
            list.add( toDTO( reader ) );
        }

        return list;
    }

    @Override
    public Reader toEntity(ReaderCreateRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Reader reader = new Reader();

        reader.setFirstName( dto.firstName() );
        reader.setLastName( dto.lastName() );
        reader.setEmail( dto.email() );
        reader.setPhone( dto.phone() );

        return reader;
    }

    @Override
    public void updateEntityFromDto(ReaderUpdateRequest dto, Reader reader) {
        if ( dto == null ) {
            return;
        }

        reader.setFirstName( dto.firstName() );
        reader.setLastName( dto.lastName() );
        reader.setEmail( dto.email() );
        reader.setPhone( dto.phone() );
    }
}
