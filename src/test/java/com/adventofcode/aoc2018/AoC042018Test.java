package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC042018Test extends Generic {

    private static final Solution INSTANCE = new AoC042018();

    public AoC042018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "[1518-11-01 00:00] Guard #10 begins shift\n" +
                        "[1518-11-01 00:05] falls asleep\n" +
                        "[1518-11-01 00:25] wakes up\n" +
                        "[1518-11-01 00:30] falls asleep\n" +
                        "[1518-11-01 00:55] wakes up\n" +
                        "[1518-11-01 23:58] Guard #99 begins shift\n" +
                        "[1518-11-02 00:40] falls asleep\n" +
                        "[1518-11-02 00:50] wakes up\n" +
                        "[1518-11-03 00:05] Guard #10 begins shift\n" +
                        "[1518-11-03 00:24] falls asleep\n" +
                        "[1518-11-03 00:29] wakes up\n" +
                        "[1518-11-04 00:02] Guard #99 begins shift\n" +
                        "[1518-11-04 00:36] falls asleep\n" +
                        "[1518-11-04 00:46] wakes up\n" +
                        "[1518-11-05 00:03] Guard #99 begins shift\n" +
                        "[1518-11-05 00:45] falls asleep\n" +
                        "[1518-11-05 00:55] wakes up", "240"},
                {FIRST, getInput(INSTANCE), "115167"},
                {SECOND, "[1518-11-01 00:00] Guard #10 begins shift\n" +
                        "[1518-11-01 00:05] falls asleep\n" +
                        "[1518-11-01 00:25] wakes up\n" +
                        "[1518-11-01 00:30] falls asleep\n" +
                        "[1518-11-01 00:55] wakes up\n" +
                        "[1518-11-01 23:58] Guard #99 begins shift\n" +
                        "[1518-11-02 00:40] falls asleep\n" +
                        "[1518-11-02 00:50] wakes up\n" +
                        "[1518-11-03 00:05] Guard #10 begins shift\n" +
                        "[1518-11-03 00:24] falls asleep\n" +
                        "[1518-11-03 00:29] wakes up\n" +
                        "[1518-11-04 00:02] Guard #99 begins shift\n" +
                        "[1518-11-04 00:36] falls asleep\n" +
                        "[1518-11-04 00:46] wakes up\n" +
                        "[1518-11-05 00:03] Guard #99 begins shift\n" +
                        "[1518-11-05 00:45] falls asleep\n" +
                        "[1518-11-05 00:55] wakes up", "4455"},
                {SECOND, getInput(INSTANCE), "32070"},
        });
    }
}