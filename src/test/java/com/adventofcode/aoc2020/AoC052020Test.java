package com.adventofcode.aoc2020;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC052020Test extends Generic {

    private static final Solution INSTANCE = new AoC052020();

    public AoC052020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "FBFBBFFRLR", "357" },
                { FIRST, "BFFFBBFRRR", "567" },
                { FIRST, "FFFBBBFRRR", "119" },
                { FIRST, "BBFFBBFRLL", "820" },
                { FIRST, getInput( INSTANCE ), "991" },
                { SECOND, getInput( INSTANCE ), "534" }
        });
    }
}
