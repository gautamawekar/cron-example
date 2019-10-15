package com.gawekar.tesco;

import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExecutionTimeCalculatorTest {
    private ExecutionTimeCalculator executionTimeCalculator = new ExecutionTimeCalculator();

    @Test
    public void providedSample() {
        //GIVEN:
        var crontabEntries = List.of("10 04 /usr/bin/foo",
                                     "* 00,15,30,45 /usr/bin/coo",
                                     "* 15 /usr/bin/baz",
                                     "10 * /usr/bin/boo",
                                     "* 00,15,30,45 /usr/bin/coo");
        var timeInForm = LocalTime.of(11, 37);


        //WHEN:
        List<String> firstExecutions = executionTimeCalculator.findFirstExecutions(timeInForm, crontabEntries);


        //THEN:
        assertEquals("1004 /usr/bin/foo", firstExecutions.get(0));
        assertEquals("1145 /usr/bin/coo", firstExecutions.get(1));
        assertEquals("1215 /usr/bin/baz", firstExecutions.get(2));
        assertEquals("1000 /usr/bin/boo", firstExecutions.get(3));
        assertEquals("1145 /usr/bin/coo", firstExecutions.get(4));
    }


    @Test
    public void edge00Hours() {
        //GIVEN:
        var crontabEntries = List.of("00 00 /usr/bin/coo");
        var timeInForm = LocalTime.of(11, 37);


        //WHEN:
        List<String> firstExecutions = executionTimeCalculator.findFirstExecutions(timeInForm, crontabEntries);


        //THEN:
        assertEquals("0000 /usr/bin/coo", firstExecutions.get(0));

    }

    @Test
    public void edge2359() {

        //GIVEN:
        var crontabEntries = List.of("23 59 /usr/bin/coo");
        var timeInForm = LocalTime.of(11, 37);


        //WHEN:
        List<String> firstExecutions = executionTimeCalculator.findFirstExecutions(timeInForm, crontabEntries);

        //THEN:
        assertEquals("2359 /usr/bin/coo", firstExecutions.get(0));
    }
}