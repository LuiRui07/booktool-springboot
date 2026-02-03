package com.booktool.backend.controller;

import com.booktool.backend.api.dto.EnrichmentStatusDto;
import com.booktool.backend.service.BookEnrichmentService;
import com.booktool.backend.service.EnrichmentStatusService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/enrichment")
@CrossOrigin(origins = "http://localhost:4200")
public class EnrichmentController {

    private final EnrichmentStatusService statusService;

    public EnrichmentController(EnrichmentStatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/status")
    public EnrichmentStatusDto status() {
        return new EnrichmentStatusDto(
                statusService.getLastEnrichment()
        );
    }
}
