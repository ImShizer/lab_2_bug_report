package ua.opnu.labwork2.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Вхідні дані для оновлення інформації про книгу")
public record BookUpdateRequest(
        @Schema(description = "Назва книги", example = "Захар Беркут", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Назва книги обов'язкова")
        @Size(min = 2, max = 150, message = "Назва повинна містити від 1 до 255 символів")
        String title,

        @Schema(description = "Міжнародний стандартний номер книги (ISBN)", example = "978-966-03-8541-2", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "ISBN обов'язковий")
        @Size(min = 10, max = 17, message = "ISBN повинен містити від 10 до 17 символів")
        String isbn,

        @Schema(description = "Рік видання", example = "1883", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Рік публікації обов'язковий")
        @Max(value = 2026, message = "Рік публікації не може бути у майбутньому")
        Integer publicationYear,

        @Schema(description = "Жанр літературного твору", example = "Історична повість", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Жанр обов'язковий")
        @Size(min = 2, max = 80, message = "Жанр повинен містити від 2 до 80 символів")
        String genre,

        @Schema(description = "Короткий анотаційний опис змісту книги", example = "Історична повість про боротьбу карпатських общин проти татаро-монгольської навали.")
        @Size(min = 10, max = 2000, message = "Опис не може перевищувати 2000 символів")
        String description,

        @Schema(description = "Ідентифікатор автора цієї книги", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "ID автора обов'язкове")
        @Positive
        Long authorId
) {}