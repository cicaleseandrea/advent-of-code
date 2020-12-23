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
public class AoC232020Test extends Generic {

    private static final Solution INSTANCE = new AoC232020();

    public AoC232020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "389125467", "67384529"  },
                { FIRST, getInput( INSTANCE ), "27865934" },
                { SECOND, "389125467", "149245887792"  },
                { SECOND, getInput( INSTANCE ), "170836011000" }
        });
    }
}
