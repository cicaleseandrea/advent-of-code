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
public class AoC242015Test extends Generic {

    private static final Solution INSTANCE = new AoC242015();

    public AoC242015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        1
                        2
                        3
                        4
                        5
                        7
                        8
                        9
                        10
                        11
                        """, "99" },
                { FIRST, getInput( INSTANCE ), "10723906903" },
                { SECOND, getInput( INSTANCE ), "74850409" }
        });
    }
}
