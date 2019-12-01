package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC162018Test extends Generic {

    private static final Solution INSTANCE = new AoC162018();

    public AoC162018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "Before: [3, 2, 1, 1]\n" +
                        "9 2 1 2\n" +
                        "After:  [3, 2, 2, 1]\n" +
                        "\n" +
                        "Before: [3, 2, 1, 1]\n" +
                        "9 2 1 2\n" +
                        "After:  [3, 2, 2, 1]", "2"},
                {FIRST, getInput(INSTANCE), "590"},
                {SECOND, getInput(INSTANCE), "475"}
        });
    }
}