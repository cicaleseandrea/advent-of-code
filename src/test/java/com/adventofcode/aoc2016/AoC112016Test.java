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
public class AoC112016Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC112016();

    public AoC112016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
                        The second floor contains a hydrogen generator.
                        The third floor contains a lithium generator.
                        The fourth floor contains nothing relevant.
                        """, "11" },
            { FIRST, getInput( INSTANCE ), "33" },
            { SECOND, getInput( INSTANCE ), "57" },
        });
    }
}
