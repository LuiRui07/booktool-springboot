package com.booktool.backend.job;

import com.booktool.backend.service.BookEnrichmentService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class BookEnrichmentJob {

    private final BookEnrichmentService enrichmentService;

    public BookEnrichmentJob(BookEnrichmentService enrichmentService) {
        this.enrichmentService = enrichmentService;
    }

    @Scheduled(fixedDelay = 60_000)
    public void enrichBooks() {
        enrichmentService.enrichBooks();
    }
}