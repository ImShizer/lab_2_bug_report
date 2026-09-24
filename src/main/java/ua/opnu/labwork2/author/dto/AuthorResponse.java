package ua.opnu.labwork2.author.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Відповідь з даними автора")
public record AuthorResponse(
        @Schema(description = "Унікальний ідентифікатор автора", example = "1")
        Long id,

        @Schema(description = "Ім'я автора", example = "Тарас")
        String firstName,

        @Schema(description = "Прізвище автора", example = "Шевченко")
        String lastName,

        @Schema(description = "Рік народження", example = "1814")
        Integer birthYear,

        @Schema(description = "Країна походження", example = "Україна")
        String country
) {}