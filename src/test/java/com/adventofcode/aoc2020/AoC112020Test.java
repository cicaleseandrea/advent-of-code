package com.adventofcode.aoc2020;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC112020Test extends Generic {

    private static final Solution INSTANCE = new AoC112020();

    public AoC112020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        L.LL.LL.LL
                        LLLLLLL.LL
                        L.L.L..L..
                        LLLL.LL.LL
                        L.LL.LL.LL
                        L.LLLLL.LL
                        ..L.L.....
                        LLLLLLLLLL
                        L.LLLLLL.L
                        L.LLLLL.LL
                        """, "37" },
                { FIRST, getInput( INSTANCE ), "2263" },
                { SECOND, """
                        L.LL.LL.LL
                        LLLLLLL.LL
                        L.L.L..L..
                        LLLL.LL.LL
                        L.LL.LL.LL
                        L.LLLLL.LL
                        ..L.L.....
                        LLLLLLLLLL
                        L.LLLLLL.L
                        L.LLLLL.LL
                        """, "26"  },
                { SECOND, getInput( INSTANCE ), "2002" }
        });
    }
}
