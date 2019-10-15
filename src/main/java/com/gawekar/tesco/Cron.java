package com.gawekar.tesco;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Cron {

    private static final DateTimeFormatter HH_MM_DATETIME_FORMAT = DateTimeFormatter.ofPattern("HHmm");
    private static final ExecutionTimeCalculator executionTimeCalculator = new ExecutionTimeCalculator();


    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Invalid parameters");
            return;
        }

        String crontabFile = args[0];
        String timeInForm = args[1];

        List<String> crontabEntries = Files.readAllLines(Path.of(crontabFile));
        List<String> firstExecutions = executionTimeCalculator.findFirstExecutions(createLocalTime(timeInForm), crontabEntries);
        firstExecutions.stream()
                       .forEach(System.out::println);
    }


    private static LocalTime createLocalTime(String hourMinute) {
        return LocalTime.parse(hourMinute, HH_MM_DATETIME_FORMAT);
    }


}
