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
public class AoC142020Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC142020();

    public AoC142020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
                        mem[8] = 11
                        mem[7] = 101
                        mem[8] = 0
                        """, "165"  },
                { FIRST, getInput( INSTANCE ), "11327140210986" },
                { SECOND, """
                        mask = 000000000000000000000000000000X1001X
                        mem[42] = 100
                        mask = 00000000000000000000000000000000X0XX
                        mem[26] = 1
                        """, "208"  },
                { SECOND, getInput( INSTANCE ), "2308180581795" }
        });
    }
}
