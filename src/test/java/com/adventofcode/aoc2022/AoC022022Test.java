package com.adventofcode.aoc2022;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC022022Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC022022();

    public AoC022022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        A Y
                        B X
                        C Z
                        """, "15" },
                { FIRST, getInput( INSTANCE ), "10718" },
                { SECOND, """
                        A Y
                        B X
                        C Z
                        """, "12" },
                { SECOND, getInput( INSTANCE ), "14652" }
        });
    }
}
