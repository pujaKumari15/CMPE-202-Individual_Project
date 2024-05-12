package com.cmpe202.analyzers;


import com.cmpe202.interfaces.LogProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogAnalyzerTest {

    private LogAnalyzer logAnalyzer;
    private LogProcessor mockLogProcessor;

    @BeforeEach
    void setUp() {
        mockLogProcessor = mock(LogProcessor.class);
        logAnalyzer = new LogAnalyzer();
    }

    @Test
    void testAnalyzeLogsWithNullProcessor() {
        List<String> logs = Arrays.asList("log1", "log2", "log3");
        assertThrows(NullPointerException.class, () -> logAnalyzer.analyzeLogs(logs));
    }

    @Test
    void testAnalyzeLogsWithMockProcessor() throws JsonProcessingException {
        List<String> logs = Arrays.asList("log1", "log2", "log3");
        String expectedOutput = "Processed Logs";

        // Mock behavior of the log processor
        when(mockLogProcessor.processLog(logs)).thenReturn(expectedOutput);

        // Set the mock processor
        logAnalyzer.setLogProcessor(mockLogProcessor);

        // Verify that the log analyzer returns the expected output
        assertEquals(expectedOutput, logAnalyzer.analyzeLogs(logs));
    }
}
