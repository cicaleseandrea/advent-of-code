package com.adventofcode.aoc2016;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC152016Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC152016();

    public AoC152016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        Disc #1 has 5 positions; at time=0, it is at position 4.
                        Disc #2 has 2 positions; at time=0, it is at position 1.
                        """, "5" },
                { FIRST, """
                        Disc #1 has 3 positions; at time=0, it is at position 2.
                        Disc #2 has 4 positions; at time=0, it is at position 3.
                        Disc #3 has 5 positions; at time=0, it is at position 3.
                        """, "39" },
                { FIRST, getInput( INSTANCE ), "148737" },
                { SECOND, getInput( INSTANCE ), "2353212" },
        });
    }
}
