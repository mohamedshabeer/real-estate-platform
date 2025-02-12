package com.ev.realestate.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PropertyResponse(
        Long id,
        String title,
        String description,
        String location,
        double price,
        Instant createdAt,
        Instant updatedAt
) {}
