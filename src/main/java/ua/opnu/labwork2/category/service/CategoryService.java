package ua.opnu.labwork2.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.labwork2.book.persistence.BookRepository;
import ua.opnu.labwork2.category.dto.CategoryCreateRequest;
import ua.opnu.labwork2.category.dto.CategoryResponse;
import ua.opnu.labwork2.category.dto.CategoryUpdateRequest;
import ua.opnu.labwork2.category.mapper.CategoryMapper;
import ua.opnu.labwork2.category.persistence.Category;
import ua.opnu.labwork2.category.persistence.CategoryRepository;
import ua.opnu.labwork2.exception.ConflictOperationException;
import ua.opnu.labwork2.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryMapper mapper;

    public List<CategoryResponse> getAll() {
        return mapper.toDTOList(repository.findAll());
    }

    public CategoryResponse getById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категорію з ID " + id + " не знайдено"));
        return mapper.toDTO(category);
    }

    public CategoryResponse create(CategoryCreateRequest request) {
        Category category = mapper.toEntity(request);
        Category savedCategory = repository.save(category);
        return mapper.toDTO(savedCategory);
    }

    public CategoryResponse update(Long id, CategoryUpdateRequest request) {
        Category existingCategory = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Категорію з ID " + id + " не знайдено"));

        mapper.updateEntityFromDto(request, existingCategory);
        Category updatedCategory = repository.save(existingCategory);
        return mapper.toDTO(updatedCategory);
    }

    @Autowired
    private BookRepository bookRepository;

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Категорію з ID " + id + " не знайдено");
        }

        if (!bookRepository.findByCategoriesId(id).isEmpty()) {
            throw new ConflictOperationException("Неможливо видалити категорію, оскільки існують книги, що належать до неї.");
        }

        repository.deleteById(id);
    }
}