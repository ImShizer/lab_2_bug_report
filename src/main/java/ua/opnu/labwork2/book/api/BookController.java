package ua.opnu.labwork2.book.api;

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
import ua.opnu.labwork2.book.dto.BookCreateRequest;
import ua.opnu.labwork2.book.dto.BookResponse;
import ua.opnu.labwork2.book.dto.BookUpdateRequest;
import ua.opnu.labwork2.book.service.BookService;
import ua.opnu.labwork2.exception.ApiErrorResponse;

import java.util.List;

@Tag(name = "Управління книгами", description = "Ендпоінти для створення, редагування, видалення, пошуку книг та керування їх категоріями")
@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(summary = "Додати нову книгу", description = "Створює нову книгу в системі. Бізнес-правило: ISBN книги повинен бути унікальним.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Книгу успішно додано", content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Книга з таким ISBN вже існує в системі", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.create(request));
    }

    @Operation(summary = "Отримати всі книги", description = "Повертає повний список книг, зареєстрованих у бібліотеці, разом із вкладеними категоріями.")
    @ApiResponse(responseCode = "200", description = "Список книг успішно отримано")
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAll() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @Operation(summary = "Знайти книгу за ID", description = "Повертає інформацію про конкретну книгу за її унікальним ідентифікатором.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Книгу успішно знайдено", content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "Книгу з таким ID не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @Operation(summary = "Оновити дані книги", description = "Редагує інформацію про існуючу книгу. Перевіряє унікальність оновленого ISBN.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Дані книги успішно оновлено", content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні вхідні дані", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Книгу або вказаного автора не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт: новий ISBN вже зайнятий іншою книгою", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id, @Valid @RequestBody BookUpdateRequest request) { // Тип змінено на BookUpdateRequest
        return ResponseEntity.ok(bookService.update(id, request));
    }

    @Operation(summary = "Видалити книгу", description = "Видаляє книгу з бази даних за її ID. Бізнес-правило: не можна видалити книгу, якщо вона зараз знаходиться на руках у читача (має активну видачу).")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Книгу успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Книгу з вказаним ID не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт: книга має активну видачу, видалення заблоковано", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Отримати книги конкретного автора", description = "Фільтрує та повертає список усіх книг, які належать автору із зазначеним ID.")
    @ApiResponse(responseCode = "200", description = "Список книг автора успішно отримано")
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BookResponse>> getByAuthor(@PathVariable Long authorId) {
        return ResponseEntity.ok(bookService.getByAuthor(authorId));
    }

    @Operation(summary = "Отримати книги за категорією", description = "Фільтрує та повертає список усіх книг, що віднесені до категорії із зазначеним ID.")
    @ApiResponse(responseCode = "200", description = "Список книг категорії успішно отримано")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BookResponse>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(bookService.getByCategory(categoryId));
    }

    @Operation(summary = "Прив'язати категорію до книги", description = "Додає до списку категорій книги нову категорію за її ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Категорію успішно прив'язано", content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "Книгу або категорію з такими ID не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/{id}/categories/{catId}")
    public ResponseEntity<BookResponse> link(@PathVariable Long id, @PathVariable Long catId) {
        return ResponseEntity.ok(bookService.addCategoryToBook(id, catId));
    }

    @Operation(summary = "Видалити категорію з книги", description = "Прибирає зв'язок між книгою та вказаною категорією.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Зв'язок з категорією успішно видалено", content = @Content(schema = @Schema(implementation = BookResponse.class))),
            @ApiResponse(responseCode = "404", description = "Книгу або категорію не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}/categories/{catId}")
    public ResponseEntity<BookResponse> removeCategory(@PathVariable Long id, @PathVariable Long catId) {
        return ResponseEntity.ok(bookService.removeCategoryFromBook(id, catId));
    }
}