package ua.opnu.labwork2.author.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ua.opnu.labwork2.author.dto.AuthorCreateRequest;
import ua.opnu.labwork2.author.dto.AuthorResponse;
import ua.opnu.labwork2.author.dto.AuthorUpdateRequest;
import ua.opnu.labwork2.author.persistence.Author;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T21:45:24+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class AuthorMapperImpl implements AuthorMapper {

    @Override
    public AuthorResponse toDTO(Author author) {
        if ( author == null ) {
            return null;
        }

        Long id = null;
        String firstName = null;
        String lastName = null;
        Integer birthYear = null;
        String country = null;

        id = author.getId();
        firstName = author.getFirstName();
        lastName = author.getLastName();
        birthYear = author.getBirthYear();
        country = author.getCountry();

        AuthorResponse authorResponse = new AuthorResponse( id, firstName, lastName, birthYear, country );

        return authorResponse;
    }

    @Override
    public List<AuthorResponse> toDTOList(List<Author> authors) {
        if ( authors == null ) {
            return null;
        }

        List<AuthorResponse> list = new ArrayList<AuthorResponse>( authors.size() );
        for ( Author author : authors ) {
            list.add( toDTO( author ) );
        }

        return list;
    }

    @Override
    public Author toEntity(AuthorCreateRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Author author = new Author();

        author.setFirstName( dto.firstName() );
        author.setLastName( dto.lastName() );
        author.setBirthYear( dto.birthYear() );
        author.setCountry( dto.country() );

        return author;
    }

    @Override
    public void updateEntityFromDto(AuthorUpdateRequest dto, Author author) {
        if ( dto == null ) {
            return;
        }

        author.setFirstName( dto.firstName() );
        author.setLastName( dto.lastName() );
        author.setBirthYear( dto.birthYear() );
        author.setCountry( dto.country() );
    }
}
