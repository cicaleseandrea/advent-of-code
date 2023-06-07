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
public class AoC152017Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC152017();

    public AoC152017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        Generator A starts with 65
                        Generator B starts with 8921
                        """, "588" },
            { FIRST, getInput( INSTANCE ), "569" },
            { SECOND, """
                        Generator A starts with 65
                        Generator B starts with 8921
                        """, "309" },
            { SECOND, getInput( INSTANCE ), "298" }
        });
    }
}
