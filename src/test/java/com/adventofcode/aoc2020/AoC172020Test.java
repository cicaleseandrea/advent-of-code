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
public class AoC172020Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC172020();

    public AoC172020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        .#.
                        ..#
                        ###
                        """, "112"  },
                { FIRST, getInput( INSTANCE ), "310" },
                { SECOND, """
                        .#.
                        ..#
                        ###
                        """, "848"  },
                { SECOND, getInput( INSTANCE ), "2056" }
        });
    }
}
