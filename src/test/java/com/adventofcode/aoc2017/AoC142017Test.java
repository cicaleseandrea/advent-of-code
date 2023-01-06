package com.adventofcode.aoc2017;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class AoC142017Test extends Generic {

    private static final Solution INSTANCE = new AoC142017();

    public AoC142017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "flqrgnkx", "8108"},
                {FIRST, getInput(INSTANCE), "8140"},
                {SECOND, "flqrgnkx", "1242"},
                {SECOND, getInput(INSTANCE), "1182"}
        });
    }
}
