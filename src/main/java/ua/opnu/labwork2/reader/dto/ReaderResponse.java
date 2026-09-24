package ua.opnu.labwork2.reader.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

@Schema(description = "Повна інформація про читача у відповіді сервера")
public record ReaderResponse(
        @Schema(description = "Унікальний ідентифікатор читача", example = "1")
        Long id,

        @Schema(description = "Ім'я", example = "Олена")
        String firstName,

        @Schema(description = "Прізвище", example = "Коваленко")
        String lastName,

        @Schema(description = "Електронна пошта", example = "olena.kovalenko@example.com")
        String email,

        @Schema(description = "Номер телефону", example = "+380501234567")
        String phone,

        @Schema(description = "Дата реєстрації", example = "2026-06-09")
        LocalDate registrationDate
) {}