package com.booktool.backend.service;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EnrichmentStatusService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EnrichmentStatusService.class);
    private volatile Instant lastEnrichment;

    public void markEnriched() {
        lastEnrichment = Instant.now();
    }

    public Instant getLastEnrichment() {
        log.info("Enrichment status requested, lastUpdate={}", lastEnrichment);
        return lastEnrichment;
    }
}