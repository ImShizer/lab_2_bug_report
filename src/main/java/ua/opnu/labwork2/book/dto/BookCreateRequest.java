package ua.opnu.labwork2.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

@Schema(description = "Вхідні дані для створення нової книги")
public record BookCreateRequest(
        @Schema(description = "Назва книги", example = "Кобзар", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Назва книги обов'язкова")
        @Size(min = 2, max = 150, message = "Назва повинна містити від 1 до 255 символів")
        String title,

        @Schema(description = "Міжнародний стандартний номер книги (ISBN)", example = "978-966-03-8124-7", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "ISBN обов'язковий")
        @Size(min = 10, max = 17, message = "ISBN повинен містити від 10 до 17 символів")
        String isbn,

        @Schema(description = "Рік першого видання книги", example = "1840", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Рік публікації обов'язковий")
        @Max(value = 2026, message = "Рік публікації не може бути у майбутньому")
        Integer publicationYear,

        @Schema(description = "Жанр літературного твору", example = "Поезія", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Жанр обов'язковий")
        @Size(min = 2, max = 80, message = "Жанр повинен містити від 2 до 80 символів")
        String genre,

        @Schema(description = "Короткий анотаційний опис змісту книги", example = "Збірка поетичних творів Тараса Шевченка, яка стала фундаментом сучасної української літератури.")
        @Size(min = 10, max = 2000, message = "Опис не може перевищувати 2000 символів")
        String description,

        @Schema(description = "Ідентифікатор автора цієї книги", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "ID автора обов'язкове")
        @Positive
        Long authorId
) {}