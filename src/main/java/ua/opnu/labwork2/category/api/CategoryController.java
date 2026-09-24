package ua.opnu.labwork2.category.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.labwork2.category.dto.CategoryCreateRequest;
import ua.opnu.labwork2.category.dto.CategoryResponse;
import ua.opnu.labwork2.category.dto.CategoryUpdateRequest;
import ua.opnu.labwork2.category.service.CategoryService;
import ua.opnu.labwork2.exception.ApiErrorResponse;

import java.util.List;

@Tag(name = "Управління категоріями", description = "Ендпоінти для створення, редагування, видалення та перегляду жанрових категорій книг")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Створити нову категорію", description = "Додає нову категорію (жанр) до системи. Назва категорії має бути унікальною.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Категорію успішно створено", content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних (наприклад, занадто короткий опис)", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Категорія з такою назвою вже існує в системі", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(request));
    }

    @Operation(summary = "Отримати всі категорії", description = "Повертає повний список усіх зареєстрованих категорій у бібліотеці.")
    @ApiResponse(responseCode = "200", description = "Список категорій успішно отримано")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @Operation(summary = "Знайти категорію за ID", description = "Повертає інформацію про конкретну категорію за її унікальним ідентифікатором.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Категорію успішно знайдено", content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Категорію з вказаним ID не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @Operation(summary = "Оновити дані категорії", description = "Редагує назву або опис існуючої категорії. Перевіряє унікальність нової назви серед інших категорій.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Категорію успішно оновлено", content = @Content(schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні вхідні дані", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Категорію з вказаним ID не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт: нова назва категорії вже зайнята", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryUpdateRequest request) { // Тип змінено на CategoryUpdateRequest
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @Operation(summary = "Видалити категорію", description = "Видаляє категорію з бази даних за її ID. Бізнес-правило: не можна видалити категорію, якщо до неї прив'язана хоча б одна книга.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Категорію успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Категорію з вказаним ID не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт: неможливо видалити категорію, оскільки вона містить книги", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}