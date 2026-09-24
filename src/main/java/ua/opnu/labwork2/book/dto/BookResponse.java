package ua.opnu.labwork2.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import ua.opnu.labwork2.category.dto.CategoryResponse;
import java.util.List;

@Schema(description = "Повна інформація про книгу у відповіді сервера")
public record BookResponse(
        @Schema(description = "Унікальний ідентифікатор книги в системі", example = "1")
        Long id,

        @Schema(description = "Назва книги", example = "Кобзар")
        String title,

        @Schema(description = "Міжнародний стандартний номер книги (ISBN)", example = "978-966-03-8124-7")
        String isbn,

        @Schema(description = "Рік публікації", example = "1840")
        Integer publicationYear,

        @Schema(description = "Жанр", example = "Поезія")
        String genre,

        @Schema(description = "Опис книги", example = "Збірка поетичних творів Тараса Шевченка.")
        String description,

        @Schema(description = "Ідентифікатор автора", example = "1")
        Long authorId,

        @Schema(description = "Список категорій (тегів), до яких віднесено цю книгу")
        List<CategoryResponse> categories
) {}