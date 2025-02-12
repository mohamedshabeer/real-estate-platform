package com.ev.realestate.ratelimit;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

public class RateLimitFilter implements Filter {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final RateLimitService rateLimitService;

    public RateLimitFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String apiKey = httpRequest.getHeader("X-API-Key");

        if (StringUtils.isEmpty(apiKey)) {
            sendError(response, HttpStatus.UNAUTHORIZED,
                    "Missing API key");
            return;
        }

        if (!rateLimitService.tryConsume(apiKey)) {
            sendError(response, HttpStatus.TOO_MANY_REQUESTS,
                    "Too many requests, please try again later.");
            return;
        }

        chain.doFilter(request, response);
    }

    private void sendError(ServletResponse response, HttpStatus status,
                           String message) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(status.value());
        httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, String> error = Map.of(
                "message", message,
                "status", status.getReasonPhrase(),
                "code", String.valueOf(status.value())
        );

        mapper.writeValue(httpResponse.getWriter(), error);
    }
}