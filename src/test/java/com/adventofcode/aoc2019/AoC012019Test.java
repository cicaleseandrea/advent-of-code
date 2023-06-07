package com.adventofcode.aoc2019;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC012019Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC012019();

    public AoC012019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "12", "2" },
                { FIRST, "14", "2" },
                { FIRST, "1969", "654" },
                { FIRST, "100756", "33583" },
                { FIRST, getInput( INSTANCE ), "3465245" },
                { SECOND, "14", "2" },
                { SECOND, "1969", "966" },
                { SECOND, "100756", "50346" },
                { SECOND, getInput( INSTANCE ), "5194970" }
        });
    }
}
