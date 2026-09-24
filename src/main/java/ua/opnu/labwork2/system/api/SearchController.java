package ua.opnu.labwork2.system.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    @GetMapping("/books")
    public ResponseEntity<String> searchBooks(@RequestParam String query) {
        return ResponseEntity.ok("Пошук книг за запитом '" + query + "' - ОК");
    }

    @GetMapping("/books/advanced")
    public ResponseEntity<String> advancedSearch(
            @RequestParam String query,
            @RequestParam Integer year,
            @RequestParam String genre) {
        return ResponseEntity.ok("Розширений пошук [запит: " + query + ", рік: " + year + ", жанр: " + genre + "] - ОК");
    }

    @GetMapping("/readers")
    public ResponseEntity<String> searchReaders(@RequestParam String query) {
        return ResponseEntity.ok("Пошук читачів за запитом '" + query + "' - ОК");
    }
}