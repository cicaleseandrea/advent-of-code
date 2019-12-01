package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC222018Test extends Generic {

    private static final Solution INSTANCE = new AoC222018();

    public AoC222018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "depth: 510\n" +
                        "target: 10,10\n", "114"},
                {FIRST, getInput(INSTANCE), "6323"},
                {SECOND, "depth: 510\n" +
                        "target: 10,10\n", "45"},
                {SECOND, getInput(INSTANCE), "982"}
        });
    }
}