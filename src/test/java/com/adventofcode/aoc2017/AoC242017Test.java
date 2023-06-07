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
public class AoC242017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC242017();

    public AoC242017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        0/2
                        2/2
                        2/3
                        3/4
                        3/5
                        0/1
                        10/1
                        9/10
                        """, "31" },
            { FIRST, getInput( INSTANCE ), "1868" },
            { SECOND, """
                        0/2
                        2/2
                        2/3
                        3/4
                        3/5
                        0/1
                        10/1
                        9/10
                        """, "19" },
            { SECOND, getInput( INSTANCE ), "1841" }
        });
    }
}
