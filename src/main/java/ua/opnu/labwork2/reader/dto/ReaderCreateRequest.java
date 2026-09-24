package ua.opnu.labwork2.reader.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Вхідні дані для реєстрації нового читача")
public record ReaderCreateRequest(
        @Schema(description = "Ім'я читача", example = "Олена", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Ім'я обов'язкове")
        @Size(min = 2, max = 50, message = "Ім'я повинно бути від 2 до 50 символів")
        String firstName,

        @Schema(description = "Прізвище читача", example = "Коваленко", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Прізвище обов'язкове")
        @Size(min = 2, max = 50, message = "Прізвище повинно бути від 2 до 50 символів")
        String lastName,

        @Schema(description = "Електронна пошта читача (повинна бути унікальною)", example = "olena.kovalenko@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email обов'язковий")
        @Email(message = "Некоректний формат email")
        String email,

        @Schema(description = "Контактний номер телефону у міжнародному форматі", example = "+380501234567", requiredMode = Schema.RequiredMode.REQUIRED)
        @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Некоректний номер телефону (наприклад, +380501234567)")
        @Size(min = 10, max = 20, message = "Номер телефону повинен бути від 10 до 20 цифр")
        String phone,

        @Schema(description = "Дата реєстрації читача у бібліотеці", example = "2026-06-09", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull
        @PastOrPresent(message = "Дата реєстрації не може бути у майбутньому")
        LocalDate registrationDate
) {}