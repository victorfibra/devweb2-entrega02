package com.example.entrega02.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class InfoController {

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of(
                "app", "Entrega02 API",
                "description", "API pública para informações",
                "status", "ok"
        );
    }
}
