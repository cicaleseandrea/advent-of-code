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
public class AoC112016Test extends Generic {

    private static final Solution INSTANCE = new AoC112016();

    public AoC112016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
                        The second floor contains a hydrogen generator.
                        The third floor contains a lithium generator.
                        The fourth floor contains nothing relevant.
                        """, "11" },
            { FIRST, getInput( INSTANCE ), "33" },
            { SECOND, getInput( INSTANCE ), "57" },
        });
    }
}
