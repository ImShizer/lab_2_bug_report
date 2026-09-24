package ua.opnu.labwork2.reader.api;

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
import ua.opnu.labwork2.loan.dto.LoanResponse;
import ua.opnu.labwork2.reader.dto.ReaderCreateRequest;
import ua.opnu.labwork2.reader.dto.ReaderResponse;
import ua.opnu.labwork2.reader.dto.ReaderUpdateRequest;
import ua.opnu.labwork2.reader.service.ReaderService;
import ua.opnu.labwork2.exception.ApiErrorResponse;

import java.util.List;

@Tag(name = "Управління читачами", description = "Ендпоінти для реєстрації нових читачів, пошуку, редагування профілів та перегляду історії їхніх оренд")
@RestController
@RequestMapping("/readers")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @Operation(summary = "Зареєструвати нового читача", description = "Додає нового користувача бібліотеки. Бізнес-правило: Email читача має бути унікальним.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Читача успішно зареєстровано", content = @Content(schema = @Schema(implementation = ReaderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних (некоректний email, телефон тощо)", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Читач з таким Email вже існує в системі", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ReaderResponse> create(@Valid @RequestBody ReaderCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(readerService.create(request));
    }

    @Operation(summary = "Отримати всіх читачів", description = "Повертає масив усіх зареєстрованих у бібліотеці читачів.")
    @ApiResponse(responseCode = "200", description = "Список успішно отримано")
    @GetMapping
    public ResponseEntity<List<ReaderResponse>> getAll() {
        return ResponseEntity.ok(readerService.getAll());
    }

    @Operation(summary = "Знайти читача за ID", description = "Повертає профіль конкретного читача за його унікальним ідентифікатором.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Читача знайдено", content = @Content(schema = @Schema(implementation = ReaderResponse.class))),
            @ApiResponse(responseCode = "404", description = "Читача з таким ID не існує", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReaderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(readerService.getById(id));
    }

    @Operation(summary = "Оновити дані читача", description = "Оновлює контактні або особисті дані читача. Новий Email також перевіряється на унікальність.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Дані успішно оновлено", content = @Content(schema = @Schema(implementation = ReaderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних даних", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Читача з таким ID не існує", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт: вказаний Email вже належить іншому читачу", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReaderResponse> update(@PathVariable Long id, @Valid @RequestBody ReaderUpdateRequest request) { // Виправлено тип на ReaderUpdateRequest
        return ResponseEntity.ok(readerService.update(id, request));
    }

    @Operation(summary = "Видалити читача", description = "Видаляє профіль читача. Бізнес-правило: неможливо видалити читача, якщо він має не закриті історії оренди книг.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Профіль читача успішно видалено"),
            @ApiResponse(responseCode = "404", description = "Читача з таким ID не існує", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт: читач ще не повернув усі книги", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        readerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Пошук читачів", description = "Дозволяє знайти читачів за ключовим словом (наприклад, частиною імені або прізвища).")
    @ApiResponse(responseCode = "200", description = "Результати пошуку успішно отримано")
    @GetMapping("/search")
    public ResponseEntity<List<ReaderResponse>> search(@RequestParam String query) {
        return ResponseEntity.ok(readerService.search(query));
    }

    @Operation(summary = "Отримати історію оренд читача", description = "Повертає список усіх книг (активних та повернутих), які брав вказаний читач.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Історію оренд успішно отримано"),
            @ApiResponse(responseCode = "404", description = "Читача з таким ID не існує", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}/loans")
    public ResponseEntity<List<LoanResponse>> getReaderLoans(@PathVariable Long id) {
        return ResponseEntity.ok(readerService.getLoansByReaderId(id));
    }
}