package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC172018Test extends Generic {

    private static final Solution INSTANCE = new AoC172018();

    public AoC172018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "x=495, y=2..7\n" +
                        "y=7, x=495..501\n" +
                        "x=501, y=3..7\n" +
                        "x=498, y=2..4\n" +
                        "x=506, y=1..2\n" +
                        "x=498, y=10..13\n" +
                        "x=504, y=10..13\n" +
                        "y=13, x=498..504", "57"},
                {FIRST, getInput(INSTANCE), "27042"},
                {SECOND, "x=495, y=2..7\n" +
                        "y=7, x=495..501\n" +
                        "x=501, y=3..7\n" +
                        "x=498, y=2..4\n" +
                        "x=506, y=1..2\n" +
                        "x=498, y=10..13\n" +
                        "x=504, y=10..13\n" +
                        "y=13, x=498..504", "29"},
                {SECOND, getInput(INSTANCE), "22214"}
        });
    }
}