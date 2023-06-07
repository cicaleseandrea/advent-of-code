package com.adventofcode.aoc2022;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC142022Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC142022();

    public AoC142022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        498,4 -> 498,6 -> 496,6
                        503,4 -> 502,4 -> 502,9 -> 494,9
                        """, "24" },
                { FIRST, getInput( INSTANCE ), "692" },
                { SECOND, """
                        498,4 -> 498,6 -> 496,6
                        503,4 -> 502,4 -> 502,9 -> 494,9
                        """, "93" },
                { SECOND, getInput( INSTANCE ), "31706" }
        });
    }
}
