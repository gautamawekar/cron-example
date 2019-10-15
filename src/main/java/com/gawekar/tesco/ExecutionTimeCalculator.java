package com.gawekar.tesco;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Calculates crontab entries execution times.
 */

public class ExecutionTimeCalculator {
    private static final DateTimeFormatter HH_MM_DATETIME_FORMAT = DateTimeFormatter.ofPattern("HHmm");

    private CrontabEntryParser crontabEntryParser = new CrontabEntryParser();//this would be injected if DI was in use

    /**
     * For given list of crontab entries and time in form, returns expected first execution times for each entry.
     *
     * @param executionTime
     * @param crontabEntries
     * @return
     */
    public List<String> findFirstExecutions(LocalTime executionTime, List<String> crontabEntries) {
        return crontabEntries.stream()
                             .map(crontabEntryParser::toCrontabEntry)//convert to crontabEntry
                             .filter(Optional::isPresent)//Select only valid entries
                             .map(Optional::get)
                             .map(job -> this.findFirstExecution(executionTime, job))
                             .collect(Collectors.toList());
    }


    private String findFirstExecution(final LocalTime executionTime, CrontabEntry job) {

        List<LocalTime> executionTimes = job.getExecutionTimes();

        if (executionTimes.isEmpty()) {
            //Throwing exception when using streams is not good.
            //But this represents some checks that can be performed.
            //For later can have graceful execution.
            throw new RuntimeException("No execution time set");
        }

        //look for execution times greater than 'executionTime'
        Optional<LocalTime> firstOptional = executionTimes.stream()
                                                          .filter(time -> time.isAfter(executionTime))
                                                          .findFirst();

        LocalTime firstExecution = firstOptional.orElse(executionTimes.get(0));//if none found get the first execution.

        return toString(job.getFileName(), firstExecution);
    }

    private String toString(String fileName, LocalTime executionTime) {
        return String.format("%s %s", HH_MM_DATETIME_FORMAT.format(executionTime), fileName);
    }
}
