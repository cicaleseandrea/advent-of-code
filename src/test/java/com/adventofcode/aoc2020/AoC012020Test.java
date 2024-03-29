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
public class AoC012020Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC012020();

    public AoC012020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        1721
                        979
                        366
                        299
                        675
                        1456
                        """, "514579" },
                { FIRST, getInput( INSTANCE ), "138379" },
                { SECOND, """
                        1721
                        979
                        366
                        299
                        675
                        1456
                        """, "241861950" },
                { SECOND, getInput( INSTANCE ), "85491920" }
        });
    }
}
