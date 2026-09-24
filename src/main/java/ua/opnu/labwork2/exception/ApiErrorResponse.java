package ua.opnu.labwork2.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;

@Schema(description = "Єдиний формат відповіді про помилку (ApiErrorResponse)")
public record ApiErrorResponse(

        @Schema(description = "Час виникнення помилки", example = "2026-06-09T21:43:16.413")
        LocalDateTime timestamp,

        @Schema(description = "HTTP-статус помилки", example = "400")
        int status,

        @Schema(description = "Назва HTTP-помилки", example = "Bad Request")
        String error,

        @Schema(description = "Загальне повідомлення про помилку", example = "Перевірте правильність заповнення полів форми")
        String message,

        @Schema(description = "URL запиту, під час якого виникла помилка", example = "/authors")
        String path,

        @Schema(description = "Помилки валідації окремих полів (назва поля -> повідомлення).",
                example = "{\"email\": \"Некоректний формат email\", \"phone\": \"довжина від 10 до 20 символів\"}")
        Map<String, String> errors
) {}