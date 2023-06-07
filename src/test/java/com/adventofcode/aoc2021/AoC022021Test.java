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
public class AoC022021Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC022021();

    public AoC022021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        forward 5
                        down 5
                        forward 8
                        up 3
                        down 8
                        forward 2
                        """, "150" },
                { FIRST, getInput( INSTANCE ), "1636725" },
                { SECOND, """
                        forward 5
                        down 5
                        forward 8
                        up 3
                        down 8
                        forward 2
                        """, "900" },
                { SECOND, getInput( INSTANCE ), "1872757425" }
        });
    }
}
