package com.adventofcode.aoc2019;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AoC042019Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC042019();

    public AoC042019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "222221-222230", "8" },
                { FIRST, "222221-222240", "15" },
                { FIRST, getInput( INSTANCE ), "1864" },
                { SECOND, "111121-111130", "1" },
                { SECOND, getInput( INSTANCE ), "1258" }
        });
    }
}
