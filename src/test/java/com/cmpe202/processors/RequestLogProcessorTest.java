package com.cmpe202.processors;

import com.cmpe202.interfaces.LogProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestLogProcessorTest {

    @Test
    void testProcessLog() throws JsonProcessingException {
        // Create an instance of RequestLogProcessor
        LogProcessor logProcessor = new RequestLogProcessor();

        // Sample request logs
        List<String> logs = List.of(
                "timestamp=2024-03-01T10:05:00Z request_url=\"/api/status\" response_status=200 response_time_ms=100",
                "timestamp=2024-03-01T10:05:05Z request_url=\"/api/data\" response_status=404 response_time_ms=150",
                "timestamp=2024-03-01T10:05:10Z request_url=\"/api/info\" response_status=200 response_time_ms=200"
        );

        // Process the logs
        String output = logProcessor.processLog(logs);

        // Verify the output JSON string
        assertTrue(output.contains("/api/status"));
        assertTrue(output.contains("/api/data"));
        assertTrue(output.contains("/api/info"));
        assertTrue(output.contains("response_times"));
        assertTrue(output.contains("status_codes"));
    }

    @Test
    void testCovertToJson() {
        // Create an instance of RequestLogProcessor
        RequestLogProcessor requestLogProcessor = new RequestLogProcessor();

        // Sample request log output map
        // Simulating response times and status codes
        Map<String, Map<String, Map<String, Long>>> requestLogOutputMap = Map.of(
                "/api/status", Map.of(
                        "response_times", Map.of(
                                "min", 100L,
                                "50_percentile", 120L,
                                "90_percentile", 150L,
                                "95_percentile", 180L,
                                "99_percentile", 200L,
                                "max", 250L
                        ),
                        "status_codes", Map.of(
                                "2XX", 1L,
                                "4XX", 0L,
                                "5XX", 0L
                        )
                )
        );

        // Convert to JSON
        String jsonString = requestLogProcessor.covertToJson(requestLogOutputMap);

        // Verify the JSON string
        assertTrue(jsonString.contains("/api/status"));
        assertTrue(jsonString.contains("response_times"));
        assertTrue(jsonString.contains("status_codes"));
    }
}
