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
public class AoC072021Test extends Generic {

    private static final Solution INSTANCE = new AoC072021();

    public AoC072021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "16,1,2,0,4,2,7,1,2,14", "37" },
                { FIRST, getInput( INSTANCE ), "344735" },
                { SECOND, "16,1,2,0,4,2,7,1,2,14", "168" },
                { SECOND, getInput( INSTANCE ), "96798233" }
        });
    }
}
