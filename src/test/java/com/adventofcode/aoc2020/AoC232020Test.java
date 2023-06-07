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
public class AoC232020Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC232020();

    public AoC232020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "389125467", "67384529"  },
                { FIRST, getInput( INSTANCE ), "27865934" },
                { SECOND, "389125467", "149245887792"  },
                { SECOND, getInput( INSTANCE ), "170836011000" }
        });
    }
}
