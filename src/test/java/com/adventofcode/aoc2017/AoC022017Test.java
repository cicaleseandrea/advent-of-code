package com.adventofcode.aoc2017;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class AoC022017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC022017();

    public AoC022017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        5 1 9 5
                        7 5 3
                        2 4 6 8""", "18"},
                {FIRST, getInput(INSTANCE), "21845"},
                {SECOND, """
                        5 9 2 8
                        9 4 7 3
                        3 8 6 5""", "9"},
                {SECOND, getInput(INSTANCE), "191"},
        });
    }
}