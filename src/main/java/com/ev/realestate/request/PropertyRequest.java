package com.ev.realestate.request;

public record PropertyRequest(
        String title,
        String description,
        String location,
        double price
) {}
