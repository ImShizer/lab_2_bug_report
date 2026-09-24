package ua.opnu.labwork2.loan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import ua.opnu.labwork2.loan.persistence.LoanStatus;

import java.time.LocalDate;

@Schema(description = "Вхідні дані для реєстрації нової видачі книги читачу")
public record LoanCreateRequest(
        @Schema(description = "Ідентифікатор читача, який бере книгу", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "ID читача обов'язкове")
        @Positive
        Long readerId,

        @Schema(description = "Ідентифікатор книги, яка видається", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "ID книги обов'язкове")
        @Positive
        Long bookId,

        @Schema(description = "Планована кінцева дата повернення книги", example = "2026-07-09", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Кінцева дата повернення обов'язкова")
        @FutureOrPresent(message = "Дата повернення не може быть в минулому")
        LocalDate dueDate,

        @Schema(description = "Дата оформлення оренди книги", example = "2026-06-09", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Дата оренди обов'язкова")
        @PastOrPresent(message = "Дата оренди не може бути у майбутньому")
        LocalDate loanDate,

        @Schema(description = "Початковий статус запису про видачу", example = "ACTIVE", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Статус обов'язковий")
        LoanStatus status
) {}