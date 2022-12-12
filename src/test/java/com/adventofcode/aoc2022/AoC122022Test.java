package com.adventofcode.aoc2022;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC122022Test extends Generic {

    private static final Solution INSTANCE = new AoC122022();

    public AoC122022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        Sabqponm
                        abcryxxl
                        accszExk
                        acctuvwj
                        abdefghi
                        """, "31" },
                { FIRST, getInput( INSTANCE ), "391" },
                { SECOND, """
                        Sabqponm
                        abcryxxl
                        accszExk
                        acctuvwj
                        abdefghi
                        """, "29" },
                { SECOND, getInput( INSTANCE ), "386" }
        });
    }
}
