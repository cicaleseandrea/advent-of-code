package com.adventofcode.aoc2021;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC172021Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC172021();

    public AoC172021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "target area: x=20..30, y=-10..-5", "45" },
                { FIRST, getInput( INSTANCE ), "7875" },
                { SECOND, "target area: x=20..30, y=-10..-5", "112" },
                { SECOND, getInput( INSTANCE ), "2321" }
        });
    }
}
