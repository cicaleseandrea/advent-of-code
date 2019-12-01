package com.adventofcode.aoc2019;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.Solution.getInput;

@RunWith(Parameterized.class)
public class AoC012019Test extends Generic {

    private static final Solution INSTANCE = new AoC012019();

    public AoC012019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "12", "2"},
                {FIRST, "14", "2"},
                {FIRST, "1969", "654"},
                {FIRST, "100756", "33583"},
                {FIRST, getInput(INSTANCE), "3465245"},
                {SECOND, "14", "2"},
                {SECOND, "1969", "966"},
                {SECOND, "100756", "50346"},
                {SECOND, getInput(INSTANCE), "5194970"}
        });
    }
}