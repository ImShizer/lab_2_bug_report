package ua.opnu.labwork2.loan.api;

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
import ua.opnu.labwork2.loan.dto.LoanCreateRequest;
import ua.opnu.labwork2.loan.dto.LoanResponse;
import ua.opnu.labwork2.loan.dto.LoanReturnRequest;
import ua.opnu.labwork2.loan.service.LoanService;
import ua.opnu.labwork2.exception.ApiErrorResponse;

import java.util.List;

@Tag(name = "Управління орендами книг", description = "Ендпоінти для видачі книг, оформлення повернень та відстеження протермінованих заборгованостей")
@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Operation(summary = "Оформити нову видачу книги", description = "Реєструє факт видачі книги читачеві. Бізнес-правило: не можна видати книгу, яка вже знаходиться на руках у когось іншого (має статус ACTIVE).")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Книгу успішно видано читачу", content = @Content(schema = @Schema(implementation = LoanResponse.class))),
            @ApiResponse(responseCode = "400", description = "Помилка валідації вхідних дат або ідентифікаторів", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Вказаного читача або книги не існує в базі даних", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Конфлікт: книга вже має активну видачу і ще не була повернута", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<LoanResponse> create(@Valid @RequestBody LoanCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.create(request));
    }

    @Operation(summary = "Отримати історію всіх видач", description = "Повертає повний список усіх записів оренди (як активних, так і закритих).")
    @ApiResponse(responseCode = "200", description = "Повний список успішно отримано")
    @GetMapping
    public ResponseEntity<List<LoanResponse>> getAll() {
        return ResponseEntity.ok(loanService.getAll());
    }

    @Operation(summary = "Знайти запис оренди за ID", description = "Повертає інформацію про конкретну операцію видачі за її унікальним ідентифікатором.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Запис про оренду успішно знайдено", content = @Content(schema = @Schema(implementation = LoanResponse.class))),
            @ApiResponse(responseCode = "404", description = "Запис з вказаним ID не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.getById(id));
    }

    @Operation(summary = "Повернути книгу (відмітити повернення)", description = "Оновлює статус оренди та фіксує дату, коли читач повернув книгу назад до бібліотеки. Книга знову стає доступною для видачі.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Повернення успішно оформлено", content = @Content(schema = @Schema(implementation = LoanResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некоректні дані запиту", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Запис про оренду з вказаним ID не знайдено", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}/return")
    public ResponseEntity<LoanResponse> markReturned(
            @PathVariable Long id,
            @Valid @RequestBody LoanReturnRequest request) {
        return ResponseEntity.ok(loanService.markReturned(id, request));
    }

    @Operation(summary = "Отримати список активних оренд", description = "Фільтрує та повертає лише ті книги, які зараз знаходяться на руках у читачів.")
    @ApiResponse(responseCode = "200", description = "Список активних оренд успішно отримано")
    @GetMapping("/active")
    public ResponseEntity<List<LoanResponse>> getActive() {
        return ResponseEntity.ok(loanService.getActiveLoans());
    }

    @Operation(summary = "Отримати список протермінованих оренд", description = "Повертає список активних видач, у яких поточна дата вже перевищила заплановану дату повернення (`dueDate`).")
    @ApiResponse(responseCode = "200", description = "Список боржників успішно отримано")
    @GetMapping("/overdue")
    public ResponseEntity<List<LoanResponse>> getOverdue() {
        return ResponseEntity.ok(loanService.getOverdueLoans());
    }
}