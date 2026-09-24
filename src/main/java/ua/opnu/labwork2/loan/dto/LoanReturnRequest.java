package ua.opnu.labwork2.loan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ua.opnu.labwork2.loan.persistence.LoanStatus;
import java.time.LocalDate;

@Schema(description = "Вхідні дані для закриття оренди (повернення книги)")
public record LoanReturnRequest(
        @Schema(description = "Фактична дата повернення книги до бібліотеки", example = "2026-06-20", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Дата повернення обов'язкова")
        LocalDate returnDate,

        @Schema(description = "Новий статус оренди після повернення", example = "RETURNED", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Статус обов'язковий")
        LoanStatus status
) {}