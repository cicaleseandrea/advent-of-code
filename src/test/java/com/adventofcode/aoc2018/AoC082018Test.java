package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC082018Test extends Generic {

    private static final Solution INSTANCE = new AoC082018();

    public AoC082018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2", "138"},
                {FIRST, getInput(INSTANCE), "43996"},
                {SECOND, "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2", "66"},
                {SECOND, getInput(INSTANCE), "35189"}
        });
    }
}