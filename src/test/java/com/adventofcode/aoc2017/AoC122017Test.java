package com.adventofcode.aoc2017;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class AoC122017Test extends Generic {

    private static final Solution INSTANCE = new AoC122017();

    public AoC122017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        0 <-> 2
                        1 <-> 1
                        2 <-> 0, 3, 4
                        3 <-> 2, 4
                        4 <-> 2, 3, 6
                        5 <-> 6
                        6 <-> 4, 5
                        """, "6" },
            { FIRST, getInput( INSTANCE ), "145" },
            { SECOND, """
                        0 <-> 2
                        1 <-> 1
                        2 <-> 0, 3, 4
                        3 <-> 2, 4
                        4 <-> 2, 3, 6
                        5 <-> 6
                        6 <-> 4, 5
                        """, "2" },
            { SECOND, getInput( INSTANCE ), "207" }
        });
    }
}
