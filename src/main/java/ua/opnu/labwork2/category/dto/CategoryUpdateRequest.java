package ua.opnu.labwork2.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Вхідні дані для оновлення існуючої категорії книг")
public record CategoryUpdateRequest(
        @Schema(description = "Нова назва категорії", example = "Кіберпанк", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Назва категорії не може бути порожньою")
        @Size(min = 2, max = 100, message = "Назва повинна містити від 2 до 100 символів")
        String name,

        @Schema(description = "Новий опис категорії", example = "Жанр наукової фантастики, що відображає занепад людської культури на тлі стрімкого технологічного прогресу в комп'ютерну еру.", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Опис категорії не може бути порожнім")
        @Size(min = 10, max = 1000, message = "Опис не може бути менше 10 і більше 1000 символів")
        String description
) {}