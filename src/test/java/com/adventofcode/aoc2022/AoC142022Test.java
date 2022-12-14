package com.adventofcode.aoc2022;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC142022Test extends Generic {

    private static final Solution INSTANCE = new AoC142022();

    public AoC142022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        498,4 -> 498,6 -> 496,6
                        503,4 -> 502,4 -> 502,9 -> 494,9
                        """, "24" },
                { FIRST, getInput( INSTANCE ), "692" },
                { SECOND, """
                        498,4 -> 498,6 -> 496,6
                        503,4 -> 502,4 -> 502,9 -> 494,9
                        """, "93" },
                { SECOND, getInput( INSTANCE ), "31706" }
        });
    }
}
