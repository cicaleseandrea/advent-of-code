package com.adventofcode.aoc2018;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.Solution.getInput;

@RunWith(Parameterized.class)
public class AoC032018Test extends Generic {

    static private Solution INSTANCE = new AoC032018();

    public AoC032018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "#1 @ 1,3: 4x4\n" +
                        "#2 @ 3,1: 4x4\n" +
                        "#3 @ 5,5: 2x2", "4"},
                {FIRST, getInput(INSTANCE), "113966"},
                {SECOND, "#1 @ 1,3: 4x4\n" +
                        "#2 @ 3,1: 4x4\n" +
                        "#3 @ 5,5: 2x2", "3"},
                {SECOND, getInput(INSTANCE), "235"},
        });
    }
}