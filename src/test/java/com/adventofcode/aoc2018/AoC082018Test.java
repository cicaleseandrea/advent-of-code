package com.adventofcode.aoc2018;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC082018Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC082018();

    public AoC082018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2", "138"},
                {FIRST, getInput(INSTANCE), "43996"},
                {SECOND, "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2", "66"},
                {SECOND, getInput(INSTANCE), "35189"}
        });
    }
}