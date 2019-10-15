package com.gawekar.tesco;

import java.time.LocalTime;
import java.util.List;

/**
 * Represents the executable and all times the cron is expected to execute.
 */
public class CrontabEntry {
    private final String fileName;
    private final List<LocalTime> executionTimes;

    public CrontabEntry(String fileName, List<LocalTime> executionTimes) {
        this.fileName = fileName;
        this.executionTimes = executionTimes;
    }

    public List<LocalTime> getExecutionTimes() {
        return executionTimes;
    }

    public String getFileName() {
        return fileName;
    }
}
