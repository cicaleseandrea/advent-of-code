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
public class AoC192016Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC192016();

    public AoC192016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, "1", "1" },
            { FIRST, "2", "1" },
            { FIRST, "3", "3" },
            { FIRST, "4", "1" },
            { FIRST, "5", "3" },
            { FIRST, "6", "5" },
            { FIRST, "7", "7" },
            { FIRST, getInput( INSTANCE ), "1841611" },
            { SECOND, "1", "1" },
            { SECOND, "2", "1" },
            { SECOND, "3", "3" },
            { SECOND, "4", "1" },
            { SECOND, "5", "2" },
            { SECOND, "6", "3" },
            { SECOND, "7", "5" },
            { SECOND, getInput( INSTANCE ), "1423634" },
        });
    }
}
