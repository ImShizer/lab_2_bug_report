package ua.opnu.labwork2.loan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ua.opnu.labwork2.loan.persistence.LoanStatus;
import java.time.LocalDate;

@Schema(description = "Повна інформація про операцію оренди у відповіді сервера")
public record LoanResponse(
        @Schema(description = "Унікальний ідентифікатор запису про оренду", example = "1")
        Long id,

        @Schema(description = "Дата видачі книги", example = "2026-06-09")
        LocalDate loanDate,

        @Schema(description = "Граничний термін повернення книги", example = "2026-07-09")
        LocalDate dueDate,

        @Schema(description = "Фактична дата повернення книги (null, якщо книга ще у читача)", example = "2026-06-20")
        LocalDate returnDate,

        @Schema(description = "Поточний статус операції оренди (ACTIVE, RETURNED, OVERDUE)", example = "RETURNED")
        LoanStatus status,

        @Schema(description = "Ідентифікатор читача", example = "1")
        Long readerId,

        @Schema(description = "Ідентифікатор виданої книги", example = "3")
        Long bookId
) {}