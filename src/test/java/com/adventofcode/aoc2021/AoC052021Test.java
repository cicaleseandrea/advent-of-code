package com.adventofcode.aoc2021;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC052021Test extends Generic {

    private static final Solution INSTANCE = new AoC052021();

    public AoC052021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        0,9 -> 5,9
                        8,0 -> 0,8
                        9,4 -> 3,4
                        2,2 -> 2,1
                        7,0 -> 7,4
                        6,4 -> 2,0
                        0,9 -> 2,9
                        3,4 -> 1,4
                        0,0 -> 8,8
                        5,5 -> 8,2
                        """, "5" },
                { FIRST, getInput( INSTANCE ), "6548" },
                { SECOND, """
                        0,9 -> 5,9
                        8,0 -> 0,8
                        9,4 -> 3,4
                        2,2 -> 2,1
                        7,0 -> 7,4
                        6,4 -> 2,0
                        0,9 -> 2,9
                        3,4 -> 1,4
                        0,0 -> 8,8
                        5,5 -> 8,2
                        """, "12" },
                { SECOND, getInput( INSTANCE ), "19663" }
        });
    }
}
