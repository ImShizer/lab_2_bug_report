package ua.opnu.labwork2.author.api;

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
import ua.opnu.labwork2.author.dto.AuthorCreateRequest;
import ua.opnu.labwork2.author.dto.AuthorResponse;
import ua.opnu.labwork2.author.dto.AuthorUpdateRequest;
import ua.opnu.labwork2.author.service.AuthorService;
import ua.opnu.labwork2.book.dto.BookResponse;
import ua.opnu.labwork2.book.service.BookService;
import ua.opnu.labwork2.exception.ApiErrorResponse;

import java.util.List;

@Tag(name = "Управління авторами", description = "Ендпоінти для створення, редагування, отримання та видалення авторів книг")
@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @Operation(summary = "Створити нового автора", description = "Додає нового автора до бази даних. Перевіряється валідація довжини полів та реалістичність року народження.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Автора успішно створено", content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних (наприклад, порожнє ім'я)", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<AuthorResponse> create(@Valid @RequestBody AuthorCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.create(request));
    }

    @Operation(summary = "Отримати список всіх авторів", description = "Повертає масив усіх авторів, зареєстрованих у системі.")
    @ApiResponse(responseCode = "200", description = "Список успішно отримано")
    @GetMapping
    public ResponseEntity<List<AuthorResponse>> getAll() {
        return ResponseEntity.ok(authorService.getAll());
    }

    @Operation(summary = "Знайти автора за ID", description = "Повертає детальну інформацію про одного автора за його унікальним ідентифікатором.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Автора знайдено", content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Автора з таким ID не існує", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getById(id));
    }

    @Operation(summary = "Оновити дані автора", description = "Оновлює інформацію про існуючого автора. Усі поля є обов'язковими для передачі.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Дані успішно оновлено", content = @Content(schema = @Schema(implementation = AuthorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Автора з таким ID не існує", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> update(@PathVariable Long id, @Valid @RequestBody AuthorUpdateRequest request) { // Виправлено тип request!
        return ResponseEntity.ok(authorService.update(id, request));
    }

    @Operation(summary = "Видалити автора", description = "Видаляє автора за ID. Неможливо видалити автора, якщо з ним пов'язані зареєстровані книги.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Автора успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Автора з таким ID не існує", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт операції: неможливо видалити автора, бо у нього є книги", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Отримати всі книги автора", description = "Повертає список книг, які належать вказаному автору.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список книг успішно отримано"),
            @ApiResponse(responseCode = "404", description = "Автора з таким ID не існує", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}/books")
    public ResponseEntity<List<BookResponse>> getAuthorBooks(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getByAuthor(id));
    }
}