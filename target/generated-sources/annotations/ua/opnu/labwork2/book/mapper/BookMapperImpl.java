package ua.opnu.labwork2.book.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.opnu.labwork2.book.dto.BookCreateRequest;
import ua.opnu.labwork2.book.dto.BookResponse;
import ua.opnu.labwork2.book.dto.BookUpdateRequest;
import ua.opnu.labwork2.book.persistence.Book;
import ua.opnu.labwork2.category.dto.CategoryResponse;
import ua.opnu.labwork2.category.mapper.CategoryMapper;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T21:54:10+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public BookResponse toDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        Long authorId = null;
        Long id = null;
        String title = null;
        String isbn = null;
        Integer publicationYear = null;
        String genre = null;
        String description = null;
        List<CategoryResponse> categories = null;

        authorId = mapAuthorToId( book.getAuthor() );
        id = book.getId();
        title = book.getTitle();
        isbn = book.getIsbn();
        publicationYear = book.getPublicationYear();
        genre = book.getGenre();
        description = book.getDescription();
        categories = categoryMapper.toDTOList( book.getCategories() );

        BookResponse bookResponse = new BookResponse( id, title, isbn, publicationYear, genre, description, authorId, categories );

        return bookResponse;
    }

    @Override
    public List<BookResponse> toDTOList(List<Book> books) {
        if ( books == null ) {
            return null;
        }

        List<BookResponse> list = new ArrayList<BookResponse>( books.size() );
        for ( Book book : books ) {
            list.add( toDTO( book ) );
        }

        return list;
    }

    @Override
    public Book toEntity(BookCreateRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Book book = new Book();

        book.setTitle( dto.title() );
        book.setIsbn( dto.isbn() );
        book.setPublicationYear( dto.publicationYear() );
        book.setGenre( dto.genre() );
        book.setDescription( dto.description() );

        return book;
    }

    @Override
    public void updateEntityFromDto(BookUpdateRequest dto, Book book) {
        if ( dto == null ) {
            return;
        }

        book.setTitle( dto.title() );
        book.setIsbn( dto.isbn() );
        book.setPublicationYear( dto.publicationYear() );
        book.setGenre( dto.genre() );
        book.setDescription( dto.description() );
    }
}
