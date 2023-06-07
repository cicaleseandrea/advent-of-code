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
public class AoC132017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC132017();

    public AoC132017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        0: 3
                        1: 2
                        4: 4
                        6: 4
                        """, "24" },
                { FIRST, getInput( INSTANCE ), "1900" },
                { SECOND, """
                        0: 3
                        1: 2
                        4: 4
                        6: 4
                        """, "10" },
                { SECOND, getInput( INSTANCE ), "3966414" }
        });
    }
}
