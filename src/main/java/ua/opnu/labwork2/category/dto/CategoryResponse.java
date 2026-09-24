package ua.opnu.labwork2.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Повна інформація про категорію у відповіді сервера")
public record CategoryResponse(
        @Schema(description = "Унікальний ідентифікатор категорії в системі", example = "1")
        Long id,

        @Schema(description = "Назва категорії", example = "Наукова фантастика")
        String name,

        @Schema(description = "Опис категорії", example = "Література про вигадані технології, космічні подорожі, альтернативне майбутнє.")
        String description
) {}