package com.cmpe202.processors;

import com.cmpe202.processors.ApmLogProcessor;
import com.cmpe202.interfaces.LogProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApmLogProcessorTest {

    @Test
    void testProcessLog() throws JsonProcessingException {
        // Create an instance of ApmLogProcessor
        LogProcessor logProcessor = new ApmLogProcessor();

        // Create sample APM logs
        List<String> logs = Arrays.asList(
                "timestamp=2024-03-01T10:05:00Z metric=cpu_usage_percent host=appserver1 value=60",
                "timestamp=2024-03-01T10:05:05Z metric=cpu_usage_percent host=appserver1 value=70",
                "timestamp=2024-03-01T10:05:10Z metric=memory_usage_percent host=appserver1 value=50"
        );

        // Process the logs
        String output = logProcessor.processLog(logs);

        // Verify the output JSON string
        assertTrue(output.contains("cpu_usage_percent"));
        assertTrue(output.contains("memory_usage_percent"));
        assertTrue(output.contains("minimum"));
        assertTrue(output.contains("max"));
        assertTrue(output.contains("median"));
        assertTrue(output.contains("average"));
    }

    @Test
    void testCovertToJson() {
        // Create an instance of ApmLogProcessor
        ApmLogProcessor apmLogProcessor = new ApmLogProcessor();

        // Create sample aggregated APM log data
        Map<String, Map<String, Double>> apmOutputMap = Map.of(
                "cpu_usage_percent", Map.of(
                        "minimum", 10.0,
                        "maximum", 90.0,
                        "median", 50.0,
                        "average", 60.0
                )
        );

        // Convert to JSON
        String jsonString = apmLogProcessor.covertToJson(apmOutputMap);

        // Verify the JSON string
        assertTrue(jsonString.contains("cpu_usage_percent"));
        assertTrue(jsonString.contains("minimum"));
        assertTrue(jsonString.contains("maximum"));
        assertTrue(jsonString.contains("median"));
        assertTrue(jsonString.contains("average"));
    }
}
