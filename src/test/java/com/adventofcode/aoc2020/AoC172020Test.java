package com.adventofcode.aoc2020;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC172020Test extends Generic {

    private static final Solution INSTANCE = new AoC172020();

    public AoC172020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        .#.
                        ..#
                        ###
                        """, "112"  },
                { FIRST, getInput( INSTANCE ), "310" },
                { SECOND, """
                        .#.
                        ..#
                        ###
                        """, "848"  },
                { SECOND, getInput( INSTANCE ), "2056" }
        });
    }
}
