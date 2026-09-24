package ua.opnu.labwork2.system.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @GetMapping("/books/count")
    public ResponseEntity<String> getBooksCount() {
        return ResponseEntity.ok("GET /analytics/books/count OK. Загальна кількість: 150");
    }

    @GetMapping("/readers/count")
    public ResponseEntity<String> getReadersCount() {
        return ResponseEntity.ok("GET /analytics/readers/count OK. Кількість читачів: 42");
    }

    @GetMapping("/loans/active")
    public ResponseEntity<String> getActiveLoansCount() {
        return ResponseEntity.ok("GET /analytics/loans/active OK. Активних видач: 12");
    }

    @GetMapping("/loans/overdue")
    public ResponseEntity<String> getOverdueLoansCount() {
        return ResponseEntity.ok("GET /analytics/loans/overdue OK. Прострочених книг: 5");
    }

    @GetMapping("/books/by-year")
    public ResponseEntity<String> getBooksByYear() {
        return ResponseEntity.ok("GET /analytics/books/by-year OK. Статистика по роках доступна");
    }
}