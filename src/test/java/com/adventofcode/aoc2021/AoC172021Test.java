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
public class AoC172021Test extends Generic {

    private static final Solution INSTANCE = new AoC172021();

    public AoC172021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "target area: x=20..30, y=-10..-5", "45" },
                { FIRST, getInput( INSTANCE ), "7875" },
                { SECOND, "target area: x=20..30, y=-10..-5", "112" },
                { SECOND, getInput( INSTANCE ), "2321" }
        });
    }
}
