package ua.opnu.labwork2.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Вхідні дані для створення нової категорії книг")
public record CategoryCreateRequest(
        @Schema(description = "Унікальна назва категорії", example = "Наукова фантастика", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Назва категорії не може бути порожньою")
        @Size(min = 2, max = 100, message = "Назва повинна містити від 2 до 100 символів")
        String name,

        @Schema(description = "Детальний опис того, які книги входять до цієї категорії", example = "Література про вигадані технології, космічні подорожі, альтернативне майбутнє та наукові припущення.", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Опис категорії не може бути порожнім")
        @Size(min = 10, max = 1000, message = "Опис не може бути менше 10 і більше 1000 символів") // Виправлено оддруківочку в макс. довжині повідомлення з 51000 на 1000
        String description
) {}