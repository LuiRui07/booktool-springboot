package com.booktool.backend.service;

import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class EnrichmentStatusService {

    private volatile Instant lastEnrichment;

    public void markEnriched() {
        lastEnrichment = Instant.now();
    }

    public Instant getLastEnrichment() {
        return lastEnrichment;
    }
}