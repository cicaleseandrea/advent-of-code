package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC132018Test extends Generic {

    private static final Solution INSTANCE = new AoC132018();

    public AoC132018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "/->-\\        \n" +
                        "|   |  /----\\\n" +
                        "| /-+--+-\\  |\n" +
                        "| | |  | v  |\n" +
                        "\\-+-/  \\-+--/\n" +
                        "  \\------/   ", "7,3"},
                //{FIRST, getInput(INSTANCE), "123,18"},
                {SECOND, "/>-<\\  \n" +
                        "|   |  \n" +
                        "| /<+-\\\n" +
                        "| | | v\n" +
                        "\\>+</ |\n" +
                        "  |   ^\n" +
                        "  \\<->/", "6,4"},
                {SECOND, getInput(INSTANCE), "71,123"}
        });
    }
}