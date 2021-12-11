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
public class AoC112021Test extends Generic {

    private static final Solution INSTANCE = new AoC112021();

    public AoC112021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        1
                        1
                        """, "1" },
                { FIRST, getInput( INSTANCE ), "2" },
                { SECOND, """
                        1
                        1
                        """, "3" },
                { SECOND, getInput( INSTANCE ), "4" }
        });
    }
}
