package ua.opnu.labwork2.system.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actuator")
public class ActuatorController {

    @GetMapping("/health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("{\"status\":\"UP\"}");
    }

    @GetMapping("/metrics")
    public ResponseEntity<String> getMetrics() {
        return ResponseEntity.ok("GET /actuator/metrics OK. Список доступних метрик: [jvm.memory, http.server.requests]");
    }

    @GetMapping("/prometheus")
    public ResponseEntity<String> getPrometheus() {
        return ResponseEntity.ok("# HELP http_requests_total\nGET /actuator/prometheus OK");
    }
}