package com.travel;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthApi {
    @GetMapping
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}
