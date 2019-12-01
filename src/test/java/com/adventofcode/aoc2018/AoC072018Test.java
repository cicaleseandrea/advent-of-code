package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC072018Test extends Generic {

    private static final Solution INSTANCE = new AoC072018();

    public AoC072018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "Step C must be finished before step A can begin.\n" +
                        "Step C must be finished before step F can begin.\n" +
                        "Step A must be finished before step B can begin.\n" +
                        "Step A must be finished before step D can begin.\n" +
                        "Step B must be finished before step E can begin.\n" +
                        "Step D must be finished before step E can begin.\n" +
                        "Step F must be finished before step E can begin.", "CABDFE"},
                {FIRST, getInput(INSTANCE), "JMQZELVYXTIGPHFNSOADKWBRUC"},
                {SECOND, "Step C must be finished before step A can begin.\n" +
                        "Step C must be finished before step F can begin.\n" +
                        "Step A must be finished before step B can begin.\n" +
                        "Step A must be finished before step D can begin.\n" +
                        "Step B must be finished before step E can begin.\n" +
                        "Step D must be finished before step E can begin.\n" +
                        "Step F must be finished before step E can begin.", "15"},
                {SECOND, getInput(INSTANCE), "1133"},
        });
    }
}