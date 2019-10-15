package com.gawekar.tesco;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * parser to parse crontab entries
 */

public class CrontabEntryParser {

    private static final String WILD_CARD = "*";

    /**
     * Returns CrontabEntry for provided crontab entry as declared inside crontab file.
     * If crontabEntryStr is not in expected format it returns nothing.
     *
     * @param crontabEntryStr
     * @return
     */
    public Optional<CrontabEntry> toCrontabEntry(String crontabEntryStr) {
        return Optional.ofNullable(crontabEntryStr.split(" "))
                       .filter(entry -> entry.length == 3)
                       .map(this::toCrontabEntry)
                       .or(() -> Optional.empty());

    }

    private CrontabEntry toCrontabEntry(String[] crontabEntryStr) {
        var executable = crontabEntryStr[2];
        var hourPattern = crontabEntryStr[0];
        var minutePattern = crontabEntryStr[1];
        return new CrontabEntry(executable, parseExecutionIntervals(hourPattern, minutePattern));
    }

    private List<LocalTime> parseExecutionIntervals(String hourPattern, String minutePattern) {
        List<Integer> possibleHours = parseHours(hourPattern);
        List<Integer> possibleMinutes = parseMinutes(minutePattern);
        List<LocalTime> executionInterval = new ArrayList<>();
        for (Integer hour : possibleHours) {
            for (Integer minute : possibleMinutes) {
                executionInterval.add(LocalTime.of(hour, minute));
            }
        }
        return executionInterval;
    }

    private List<Integer> parseHours(String hoursPattern) {
        if (WILD_CARD.equals(hoursPattern)) {
            return IntStream.rangeClosed(0, 23)
                            .mapToObj(Integer::valueOf)
                            .collect(Collectors.toList());
        }
        String hours[] = hoursPattern.split(",");
        return Stream.of(hours)
                     .map(Integer::valueOf)
                     .collect(Collectors.toList());
    }


    private List<Integer> parseMinutes(String minutesPattern) {
        if (WILD_CARD.equals(minutesPattern)) {
            return IntStream.rangeClosed(0, 59)
                            .mapToObj(Integer::valueOf)
                            .collect(Collectors.toList());
        }
        String minutes[] = minutesPattern.split(",");
        return Stream.of(minutes)
                     .map(Integer::valueOf)
                     .collect(Collectors.toList());
    }

}
