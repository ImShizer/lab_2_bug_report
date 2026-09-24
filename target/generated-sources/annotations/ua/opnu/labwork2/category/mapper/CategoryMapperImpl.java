package ua.opnu.labwork2.category.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ua.opnu.labwork2.category.dto.CategoryCreateRequest;
import ua.opnu.labwork2.category.dto.CategoryResponse;
import ua.opnu.labwork2.category.dto.CategoryUpdateRequest;
import ua.opnu.labwork2.category.persistence.Category;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-09T22:02:23+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResponse toDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;

        id = category.getId();
        name = category.getName();
        description = category.getDescription();

        CategoryResponse categoryResponse = new CategoryResponse( id, name, description );

        return categoryResponse;
    }

    @Override
    public List<CategoryResponse> toDTOList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryResponse> list = new ArrayList<CategoryResponse>( categories.size() );
        for ( Category category : categories ) {
            list.add( toDTO( category ) );
        }

        return list;
    }

    @Override
    public Category toEntity(CategoryCreateRequest dto) {
        if ( dto == null ) {
            return null;
        }

        Category category = new Category();

        category.setName( dto.name() );
        category.setDescription( dto.description() );

        return category;
    }

    @Override
    public void updateEntityFromDto(CategoryUpdateRequest dto, Category category) {
        if ( dto == null ) {
            return;
        }

        category.setName( dto.name() );
        category.setDescription( dto.description() );
    }
}
