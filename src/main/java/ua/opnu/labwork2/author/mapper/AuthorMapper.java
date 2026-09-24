package ua.opnu.labwork2.author.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.opnu.labwork2.author.dto.AuthorCreateRequest;
import ua.opnu.labwork2.author.dto.AuthorResponse;
import ua.opnu.labwork2.author.dto.AuthorUpdateRequest;
import ua.opnu.labwork2.author.persistence.Author;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorResponse toDTO(Author author);

    List<AuthorResponse> toDTOList(List<Author> authors);

    Author toEntity(AuthorCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    void updateEntityFromDto(AuthorUpdateRequest dto, @MappingTarget Author author);
}