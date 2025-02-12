package com.ev.realestate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RateLimitIntegrationTest {

    private static final String API_URL = "/api/properties";
    private static final String API_KEY_HEADER = "X-API-Key";
    private static final String TEST_KEY_1 = "test-key-1";
    private static final String TEST_KEY_2 = "test-key-2";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Timeout(5)
    void shouldAllow50RequestsPerMinuteAndReject51st() throws Exception {
        // Testing with parallel execution
        IntStream.rangeClosed(1, 50).parallel().forEach(i -> performValidRequest(TEST_KEY_1));

        // Check 51st request is rejected
        mockMvc.perform(get(API_URL).header(API_KEY_HEADER, TEST_KEY_1))
                .andExpectAll(
                        status().isTooManyRequests(),
                        jsonPath("$.message").value("Too many requests, please try again later.")
                );
    }

    @Test
    @Timeout(2)
    void shouldReturnUnauthorizedWhenApiKeyIsMissing() throws Exception {
        mockMvc.perform(get(API_URL))
                .andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.message").value("Missing API key")
                );
    }

    @Test
    @Timeout(5)
    void shouldMaintainSeparateRateLimitsForDifferentUsers() throws Exception {
        // Exhaust first user's quota
        IntStream.rangeClosed(1, 50).parallel().forEach(i -> performValidRequest(TEST_KEY_1));

        // Verify second user can still make requests
        IntStream.rangeClosed(1, 10).parallel().forEach(i -> performValidRequest(TEST_KEY_2));

        // Verify first user is rejected
        mockMvc.perform(get(API_URL).header(API_KEY_HEADER, TEST_KEY_1))
                .andExpect(status().isTooManyRequests());
    }

    private void performValidRequest(String apiKey) {
        try {
            mockMvc.perform(get(API_URL).header(API_KEY_HEADER, apiKey))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new AssertionError("Request failed for API key: " + apiKey, e);
        }
    }
}