package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;


@RunWith(Parameterized.class)
public class AoC012018Test extends Generic {

    private static final Solution INSTANCE = new AoC012018();

    public AoC012018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        +1
                        +1
                        +1""", "3"},
                {FIRST, """
                        +1
                        +1
                        -2""", "0"},
                {FIRST, """
                        -1
                        -2
                        -3""", "-6"},
                {FIRST, getInput(INSTANCE), "470"},
                {SECOND, """
                        +1
                        -1""", "0"},
                {SECOND, """
                        +3
                        +3
                        +4
                        -2
                        -4""", "10"},
                {SECOND, """
                        -6
                        +3
                        +8
                        +5
                        -6""", "5"},
                {SECOND, """
                        +7
                        +7
                        -2
                        -7
                        -4""", "14"},
                {SECOND, getInput(INSTANCE), "790"}
        });
    }
}