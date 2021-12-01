package com.adventofcode.aoc2021;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC012021Test extends Generic {

    private static final Solution INSTANCE = new AoC012021();

    public AoC012021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        199
                        200
                        208
                        210
                        200
                        207
                        240
                        269
                        260
                        263
                        """, "7" },
                { FIRST, getInput( INSTANCE ), "1583" },
                { SECOND, """
                        607
                        618
                        618
                        617
                        647
                        716
                        769
                        792
                        """, "5" },
                { SECOND, getInput( INSTANCE ), "1627" }
        });
    }
}
