package ua.opnu.labwork2.system.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/actuator")
public class ActuatorController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> getHealth() {
        return ResponseEntity.ok(Map.of("status", "UP"));
    }

    @GetMapping("/metrics")
    public ResponseEntity<Map<String, Object>> getMetrics() {
        return ResponseEntity.ok(Map.of(
                "status", "OK",
                "metrics", List.of("jvm.memory", "http.server.requests")
        ));
    }

    @GetMapping("/prometheus")
    public ResponseEntity<String> getPrometheus() {
        return ResponseEntity.ok("# HELP http_requests_total\nhttp_requests_total 10");
    }
}