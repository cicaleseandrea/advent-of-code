package com.adventofcode.aoc2020;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC052020Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC052020();

    public AoC052020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "FBFBBFFRLR", "357" },
                { FIRST, "BFFFBBFRRR", "567" },
                { FIRST, "FFFBBBFRRR", "119" },
                { FIRST, "BBFFBBFRLL", "820" },
                { FIRST, getInput( INSTANCE ), "991" },
                { SECOND, getInput( INSTANCE ), "534" }
        });
    }
}
