package com.adventofcode.aoc2021;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC212021Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC212021();

    public AoC212021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        Player 1 starting position: 4
                        Player 2 starting position: 8
                        """, "739785" },
                { FIRST, getInput( INSTANCE ), "605070" },
                { SECOND, """
                        Player 1 starting position: 4
                        Player 2 starting position: 8
                        """, "444356092776315" },
                { SECOND, getInput( INSTANCE ), "218433063958910" }
        });
    }

}
