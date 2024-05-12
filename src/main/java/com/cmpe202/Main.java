package com.cmpe202;

import com.cmpe202.analyzers.LogAnalyzer;
import com.cmpe202.processors.ApmLogProcessor;
import com.cmpe202.processors.AppLogProcessor;
import com.cmpe202.processors.RequestLogProcessor;
import com.cmpe202.writer.OutputFileWriter;


import java.io.File;
import java.util.List;

import static com.cmpe202.utility.LogParserUtil.parseInputFile;

public class Main {

    private final static String APM_LOG_FILE =  "apm.json";
    private final static String APP_LOG_FILE =  "application.json";
    private final static String REQ_LOG_FILE =  "request.json";
    public static void main(String[] args) {
        try {

            if (args.length != 2 || !args[0].equals("--file")) {
                System.err.println("Usage: java LogAnalyzer --file <filename>");
                System.exit(1);
            }

            //Store the filename
            String filename = args[1];
            if (filename == null) {
                System.err.println("File is invalid");
                System.exit(1);
            } else {
                File file = new File(filename);
                List<String> logs = parseInputFile(file);

                //Create an object of context class of strategy pattern
                LogAnalyzer logAnalyzer = new LogAnalyzer();

                //Set strategy based on Log Processor type

                //Call APM Log Strategy
                logAnalyzer.setLogProcessor(new ApmLogProcessor());
                String apmLogsJson = logAnalyzer.analyzeLogs(logs);

                //Call Application Log Strategy
                logAnalyzer.setLogProcessor(new AppLogProcessor());
                String appLogsJson = logAnalyzer.analyzeLogs(logs);

                //Call Request Log Strategy
                logAnalyzer.setLogProcessor(new RequestLogProcessor());
                String requestLogsJson = logAnalyzer.analyzeLogs(logs);

                //Write logs to output files
                OutputFileWriter outputFileWriter = new OutputFileWriter();
                outputFileWriter.writeToFile(apmLogsJson, APM_LOG_FILE);
                outputFileWriter.writeToFile(appLogsJson, APP_LOG_FILE);
                outputFileWriter.writeToFile(requestLogsJson, REQ_LOG_FILE);
            }
        }

        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

}