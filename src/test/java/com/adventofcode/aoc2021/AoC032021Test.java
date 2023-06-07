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
public class AoC032021Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC032021();

    public AoC032021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        00100
                        11110
                        10110
                        10111
                        10101
                        01111
                        00111
                        11100
                        10000
                        11001
                        00010
                        01010
                        """, "198" },
                { FIRST, getInput( INSTANCE ), "852500" },
                { SECOND, """
                        00100
                        11110
                        10110
                        10111
                        10101
                        01111
                        00111
                        11100
                        10000
                        11001
                        00010
                        01010
                        """, "230" },
                { SECOND, getInput( INSTANCE ), "1007985" }
        });
    }
}
