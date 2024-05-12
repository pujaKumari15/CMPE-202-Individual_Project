package com.cmpe202.processors;

import com.cmpe202.interfaces.LogProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppLogProcessor implements LogProcessor {

    public String processLog(List<String> logs) {
        Map<String, Long> appLogMap = new LinkedHashMap<>();

        System.out.println("Analyzing Application Log Processor");


        // Define the regex pattern
        String regex = "level=(\\w+)";
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        for(String line : logs) {

            // Create a Matcher object
            Matcher matcher = pattern.matcher(line);

            // Check if the pattern is found in the line
            if(matcher.find()) {
                //Extract key and value

                String key = matcher.group(1);

                //Store in map
                appLogMap.put(key, appLogMap.getOrDefault(key, 0L)+1);
            }

        }

        return covertToJson(appLogMap);

    }


    public String covertToJson(Map<String, Long> appOutputMap) {
        String jsonString = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(appOutputMap);
        }
        catch(JsonProcessingException e) {
            System.err.println("Application Log Processor - + " + e.getMessage());
        }

        return jsonString;
    }
}
