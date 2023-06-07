package com.adventofcode.aoc2016;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC252016Test extends Generic {

    private static final Solution INSTANCE = new AoC252016();

    public AoC252016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, getInput( INSTANCE ), "196" },
            { SECOND, getInput( INSTANCE ), MERRY_CHRISTMAS },
        });
    }
}
