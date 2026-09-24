package ua.opnu.labwork2.category.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ua.opnu.labwork2.category.dto.CategoryCreateRequest;
import ua.opnu.labwork2.category.dto.CategoryResponse;
import ua.opnu.labwork2.category.dto.CategoryUpdateRequest;
import ua.opnu.labwork2.category.persistence.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toDTO(Category category);

    List<CategoryResponse> toDTOList(List<Category> categories);

    @Mapping(target = "books", ignore = true)
    Category toEntity(CategoryCreateRequest dto);

    @Mapping(target = "books", ignore = true)
    void updateEntityFromDto(CategoryUpdateRequest dto, @MappingTarget Category category);
}