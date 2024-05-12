package com.cmpe202.writer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OutputFileWriterTest {

    @Test
    void testWriteToFile(@TempDir File tempDir) throws IOException {
        // Create a temporary file path
        String filename = tempDir.getAbsolutePath() + File.separator + "output.txt";

        // Create an instance of OutputFileWriter
        OutputFileWriter outputFileWriter = new OutputFileWriter();

        // Write content to the temporary file
        String content = "Line 1\nLine 2\nLine 3";
        outputFileWriter.writeToFile(content, filename);

        // Read the content of the written file
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        }

        // Verify that the content was written correctly
        assertEquals(content, fileContent.toString().trim());
    }

    @Test
    void testWriteToFileWithIOException() {
        // Create an instance of OutputFileWriter
        OutputFileWriter outputFileWriter = new OutputFileWriter();

        // Try to write to a non-existing directory
        String filename = "/invalid/directory/output.txt";
        String content = "Test content";

        // Verify that an IOException is thrown
        assertThrows(RuntimeException.class, () -> outputFileWriter.writeToFile(content, filename));
    }
}
