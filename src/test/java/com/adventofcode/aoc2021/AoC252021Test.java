package com.adventofcode.aoc2021;

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
public class AoC252021Test extends Generic {

    private static final Solution INSTANCE = new AoC252021();

    public AoC252021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        v...>>.vv>
                        .vv>>.vv..
                        >>.>v>...v
                        >>v>>.>.v.
                        v>v.vv.v..
                        >.>>..v...
                        .vv..>.>v.
                        v.v..>>v.v
                        ....v..v.>
                        """, "58" },
                { FIRST, getInput( INSTANCE ), "530" },
                { SECOND, getInput( INSTANCE ), MERRY_CHRISTMAS }
        });
    }

}
