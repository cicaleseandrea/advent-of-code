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
public class AoC082022Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC082022();

    public AoC082022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        30373
                        25512
                        65332
                        33549
                        35390
                        """, "21" },
                { FIRST, getInput( INSTANCE ), "1684" },
                { SECOND, """
                        30373
                        25512
                        65332
                        33549
                        35390
                        """, "8" },
                { SECOND, getInput( INSTANCE ), "486540" }
        });
    }
}
