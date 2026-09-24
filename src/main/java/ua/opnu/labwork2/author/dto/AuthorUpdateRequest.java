package ua.opnu.labwork2.author.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Вхідні дані для оновлення існуючого автора")
public record AuthorUpdateRequest(

        @Schema(description = "Ім'я автора", example = "Ліна", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Ім'я автора не може бути порожнім")
        @Size(min = 2, max = 50, message = "Ім'я має містити від 2 до 50 символів")
        String firstName,

        @Schema(description = "Прізвище автора", example = "Костенко", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Прізвище автора не може бути порожнім")
        @Size(min = 2, max = 50, message = "Прізвище має містити від 2 до 50 символів")
        String lastName,

        @Schema(description = "Рік народження", example = "1930", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Рік народження обов'язковий")
        @Min(value = 1000, message = "Рік народження не може бути меншим за 1000")
        @Max(value = 2026, message = "Рік народження не може бути більшим за поточний рік")
        Integer birthYear,

        @Schema(description = "Країна походження", example = "Україна", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Країна не може бути порожньою")
        @Size(min = 2, max = 100)
        String country
) {}