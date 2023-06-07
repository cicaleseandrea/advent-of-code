package com.adventofcode.aoc2016;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC082016Test extends Generic {

    private static final Solution INSTANCE = new AoC082016();

    public AoC082016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        rect 3x2
                        rotate column x=1 by 1
                        rotate row y=0 by 4
                        rotate column x=1 by 1
                        """, "6" },
                { FIRST, getInput( INSTANCE ), "121" },
                { SECOND, getInput( INSTANCE ), """
                        ⬛⬛⬛⬜⬜⬛⬜⬜⬛⬜⬛⬛⬛⬜⬜⬛⬜⬜⬛⬜⬜⬛⬛⬜⬜⬛⬛⬛⬛⬜⬜⬛⬛⬜⬜⬛⬛⬛⬛⬜⬜⬛⬛⬛⬜⬛⬜⬜⬜⬜
                        ⬛⬜⬜⬛⬜⬛⬜⬜⬛⬜⬛⬜⬜⬛⬜⬛⬜⬜⬛⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬜⬜⬛⬜⬜⬛⬜⬜⬜⬜
                        ⬛⬜⬜⬛⬜⬛⬜⬜⬛⬜⬛⬜⬜⬛⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬛⬛⬛⬜⬜⬛⬜⬜⬛⬜⬛⬛⬛⬜⬜⬜⬜⬛⬜⬜⬛⬜⬜⬜⬜
                        ⬛⬛⬛⬜⬜⬛⬜⬜⬛⬜⬛⬛⬛⬜⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬛⬜⬜⬜⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬜⬜⬛⬜⬜⬛⬜⬜⬜⬜
                        ⬛⬜⬛⬜⬜⬛⬜⬜⬛⬜⬛⬜⬛⬜⬜⬛⬜⬜⬛⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬛⬜⬜⬛⬜⬛⬜⬜⬜⬜⬜⬜⬛⬜⬜⬛⬜⬜⬜⬜
                        ⬛⬜⬜⬛⬜⬜⬛⬛⬜⬜⬛⬜⬜⬛⬜⬜⬛⬛⬜⬜⬜⬛⬛⬜⬜⬛⬛⬛⬛⬜⬜⬛⬛⬜⬜⬛⬛⬛⬛⬜⬜⬛⬛⬛⬜⬛⬛⬛⬛⬜""" },
        });
    }
}
