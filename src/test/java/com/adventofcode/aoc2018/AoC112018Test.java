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
public class AoC112018Test extends Generic {

    private static final Solution INSTANCE = new AoC112018();

    public AoC112018Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "18", "33,45"},
                {FIRST, "42", "21,61"},
                {FIRST, getInput(INSTANCE), "34,72"},
                {SECOND, "18", "90,269,16"},
                {SECOND, "42", "232,251,12"},
                {SECOND, getInput(INSTANCE), "233,187,13"}
        });
    }
}