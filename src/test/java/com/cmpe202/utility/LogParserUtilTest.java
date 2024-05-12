package com.cmpe202.utility;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogParserUtilTest {

    @Test
    void testParseInputFile(@TempDir File tempDir) throws IOException {
        // Create a temporary file with some content
        File inputFile = new File(tempDir, "test.log");
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write("Line 1\n");
            writer.write("Line 2\n");
            writer.write("Line 3\n");
        }

        // Call the parseInputFile method to read the file
        List<String> fileContent = LogParserUtil.parseInputFile(inputFile);

        // Check if the content was read correctly
        assertEquals(3, fileContent.size());
        assertTrue(fileContent.contains("Line 1"));
        assertTrue(fileContent.contains("Line 2"));
        assertTrue(fileContent.contains("Line 3"));
    }

    @Test
    void testParseInputFileWithEmptyFile(@TempDir File tempDir) throws IOException {
        // Create an empty temporary file
        File inputFile = new File(tempDir, "empty.log");
        inputFile.createNewFile();

        // Call the parseInputFile method to read the empty file
        List<String> fileContent = LogParserUtil.parseInputFile(inputFile);

        // Check if the content list is empty
        assertTrue(fileContent.isEmpty());
    }
}
