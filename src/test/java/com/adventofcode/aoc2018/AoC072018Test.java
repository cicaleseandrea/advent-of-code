package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC072018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC072018();

    public AoC072018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        Step C must be finished before step A can begin.
                        Step C must be finished before step F can begin.
                        Step A must be finished before step B can begin.
                        Step A must be finished before step D can begin.
                        Step B must be finished before step E can begin.
                        Step D must be finished before step E can begin.
                        Step F must be finished before step E can begin.""", "CABDFE"},
                {FIRST, getInput(INSTANCE), "JMQZELVYXTIGPHFNSOADKWBRUC"},
                {SECOND, """
                        Step C must be finished before step A can begin.
                        Step C must be finished before step F can begin.
                        Step A must be finished before step B can begin.
                        Step A must be finished before step D can begin.
                        Step B must be finished before step E can begin.
                        Step D must be finished before step E can begin.
                        Step F must be finished before step E can begin.""", "15"},
                {SECOND, getInput(INSTANCE), "1133"},
        });
    }
}