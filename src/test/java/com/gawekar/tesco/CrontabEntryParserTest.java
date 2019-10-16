package com.gawekar.tesco;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrontabEntryParserTest {
    private CrontabEntryParser crontabEntryParser = new CrontabEntryParser();

    @Test
    public void allHoursOfTheDay() {
        //GIVEN:
        var crontabEntryStr = "* 00,15,30,45 /some/executable";

        //WHEN:
        var cronjobs = crontabEntryParser.toCrontabEntry(crontabEntryStr);

        //THEN:
        //For 1hr  = 4 entries
        //For 24hr = 24 * 4 = 96 entries.
        assertEquals(true, cronjobs.isPresent());
        assertEquals(96, cronjobs.get().getExecutionTimes().size());
    }

    @Test
    public void allMinutesOfTheHour() {
        //GIVEN:
        var crontabEntryStr = "10 * /some/executable";

        //WHEN:
        var cronjobs = crontabEntryParser.toCrontabEntry(crontabEntryStr);

        //THEN:
        //For 1hr  = 60 entries
        assertEquals(true, cronjobs.isPresent());
        assertEquals(60, cronjobs.get().getExecutionTimes().size());
        assertEquals("/some/executable", cronjobs.get().getFileName());
    }


    @Test
    public void multipleHoursAndMultipleMinutes() {
        //GIVEN:
        var crontabEntryStr = "10,11 00,15,30,45 /some/executable";

        //WHEN:
        var cronjobs = crontabEntryParser.toCrontabEntry(crontabEntryStr);


        //THEN:
        var crontabEntry = cronjobs.get();
        //1hr =4 entries
        //2 hrs = 2 * 4 = 8 entries.
        //execution hours 10,11
        assertEquals(true, cronjobs.isPresent());
        assertEquals(8, crontabEntry.getExecutionTimes().size());
        assertEquals(LocalTime.of(10, 00), crontabEntry.getExecutionTimes().get(0));
        assertEquals(LocalTime.of(10, 15), crontabEntry.getExecutionTimes().get(1));
        assertEquals(LocalTime.of(10, 30), crontabEntry.getExecutionTimes().get(2));
        assertEquals(LocalTime.of(10, 45), crontabEntry.getExecutionTimes().get(3));
        assertEquals(LocalTime.of(11, 00), crontabEntry.getExecutionTimes().get(4));
        assertEquals(LocalTime.of(11, 15), crontabEntry.getExecutionTimes().get(5));
        assertEquals(LocalTime.of(11, 30), crontabEntry.getExecutionTimes().get(6));
        assertEquals(LocalTime.of(11, 45), crontabEntry.getExecutionTimes().get(7));
    }

    @Test
    public void specificHourAndSpecificMinute1() {
        //GIVEN:
        var crontabEntryStr = "1 15 /some/executable";

        //WHEN:
        var cronjobs = crontabEntryParser.toCrontabEntry(crontabEntryStr);


        //THEN:
        var crontabEntry = cronjobs.get();

        //should execute only at 1:15
        assertEquals(1, crontabEntry.getExecutionTimes().size());
        assertEquals(LocalTime.of(1, 15), crontabEntry.getExecutionTimes().get(0));
    }

    @Test
    public void specificHourAndSpecificMinute2() {
        //GIVEN:
        var crontabEntryStr = "10 04 /some/executable";

        //WHEN:
        var cronjobs = crontabEntryParser.toCrontabEntry(crontabEntryStr);


        //THEN:
        var crontabEntry = cronjobs.get();

        //should execute only at 10:04
        assertEquals(1, crontabEntry.getExecutionTimes().size());
        assertEquals(LocalTime.of(10, 4), crontabEntry.getExecutionTimes().get(0));
    }

}