package com.cmpe202.processors;

import com.cmpe202.interfaces.LogProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppLogProcessorTest {

    @Test
    void testProcessLog() throws JsonProcessingException {
        // Create an instance of AppLogProcessor
        LogProcessor logProcessor = new AppLogProcessor();

        // Sample application logs
        List<String> logs = List.of(
                "timestamp=2024-03-01T10:05:00Z level=INFO message=\"Backup process initiated\" host=appserver1",
                "timestamp=2024-03-01T10:05:05Z level=INFO message=\"Backup process initiated\" host=appserver1",
                "timestamp=2024-03-01T10:05:10Z level=ERROR message=\"File not found\" host=appserver1"
        );

        // Process the logs
        String output = logProcessor.processLog(logs);

        // Verify the output JSON string
        assertTrue(output.contains("INFO"));
        assertTrue(output.contains("ERROR"));
    }

    @Test
    void testCovertToJson() {
        // Create an instance of AppLogProcessor
        AppLogProcessor appLogProcessor = new AppLogProcessor();

        // Sample application output map
        Map<String, Long> appOutputMap = Map.of(
                "INFO", 2L,
                "ERROR", 1L
        );

        // Convert to JSON
        String jsonString = appLogProcessor.covertToJson(appOutputMap);

        // Verify the JSON string
        assertTrue(jsonString.contains("INFO"));
        assertTrue(jsonString.contains("ERROR"));
        assertTrue(jsonString.contains("2"));
        assertTrue(jsonString.contains("1"));
    }
}
