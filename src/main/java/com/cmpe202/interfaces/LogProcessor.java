package com.cmpe202.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface LogProcessor {

    String processLog(List<String> logs) throws JsonProcessingException;
}
