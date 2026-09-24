package ua.opnu.labwork2.reader.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.opnu.labwork2.reader.dto.ReaderCreateRequest;
import ua.opnu.labwork2.reader.dto.ReaderResponse;
import ua.opnu.labwork2.reader.dto.ReaderUpdateRequest;
import ua.opnu.labwork2.reader.persistence.Reader;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReaderMapper {

    ReaderResponse toDTO(Reader reader);

    List<ReaderResponse> toDTOList(List<Reader> readers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "loans", ignore = true)
    Reader toEntity(ReaderCreateRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "loans", ignore = true)
    void updateEntityFromDto(ReaderUpdateRequest dto, @MappingTarget Reader reader);
}