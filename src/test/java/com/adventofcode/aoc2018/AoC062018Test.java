package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC062018Test extends Generic {

    private static final Solution INSTANCE = new AoC062018();

    public AoC062018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "1, 1\n" +
                        "1, 6\n" +
                        "8, 3\n" +
                        "3, 4\n" +
                        "5, 5\n" +
                        "8, 9", "17"},
                {FIRST, getInput(INSTANCE), "4589"},
                {SECOND, "1, 1\n" +
                        "1, 6\n" +
                        "8, 3\n" +
                        "3, 4\n" +
                        "5, 5\n" +
                        "8, 9", "16"},
                {SECOND, getInput(INSTANCE), "40252"},
        });
    }
}