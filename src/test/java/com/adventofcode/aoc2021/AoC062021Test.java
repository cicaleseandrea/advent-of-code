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
public class AoC062021Test extends Generic {

    private static final Solution INSTANCE = new AoC062021();

    public AoC062021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "3,4,3,1,2", "5934" },
                { FIRST, getInput( INSTANCE ), "396210" },
                { SECOND, "3,4,3,1,2", "26984457539" },
                { SECOND, getInput( INSTANCE ), "1770823541496" }
        });
    }
}
