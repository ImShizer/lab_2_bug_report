package ua.opnu.labwork2.book.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.opnu.labwork2.author.persistence.Author;
import ua.opnu.labwork2.book.dto.BookCreateRequest;
import ua.opnu.labwork2.book.dto.BookResponse;
import ua.opnu.labwork2.book.dto.BookUpdateRequest;
import ua.opnu.labwork2.book.persistence.Book;
import ua.opnu.labwork2.category.mapper.CategoryMapper; // Додали імпорт

import java.util.List;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface BookMapper {

    @Mapping(source = "author", target = "authorId")
    BookResponse toDTO(Book book);

    List<BookResponse> toDTOList(List<Book> books);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Book toEntity(BookCreateRequest dto);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateEntityFromDto(BookUpdateRequest dto, @MappingTarget Book book);

    default Long mapAuthorToId(Author author) {
        if (author == null) {
            return null;
        }
        return author.getId();
    }
}