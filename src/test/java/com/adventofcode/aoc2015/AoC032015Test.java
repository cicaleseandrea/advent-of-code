package com.adventofcode.aoc2015;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC032015Test extends Generic {

    private static final Solution INSTANCE = new AoC032015();

    public AoC032015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, ">", "2"},
                {FIRST, "^>v<", "4"},
                {FIRST, "^v^v^v^v^v", "2"},
                {FIRST, getInput(INSTANCE), "2572"},
                {SECOND, "^v", "3"},
                {SECOND, "^>v<", "3"},
                {SECOND, "^v^v^v^v^v", "11"},
                {SECOND, getInput(INSTANCE), "2631"},
        });
    }
}