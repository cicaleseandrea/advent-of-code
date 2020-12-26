package com.adventofcode.aoc2020;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC252020Test extends Generic {

    private static final Solution INSTANCE = new AoC252020();

    public AoC252020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        5764801
                        17807724""", "14897079"  },
                { FIRST, getInput( INSTANCE ), "12227206" },
                { SECOND, getInput( INSTANCE ), MERRY_CHRISTMAS }
        });
    }
}
