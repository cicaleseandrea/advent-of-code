package com.adventofcode.aoc2020;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC022020Test extends Generic {

    private static final Solution INSTANCE = new AoC022020();

    public AoC022020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        1-3 a: abcde
                        1-3 b: cdefg
                        2-9 c: ccccccccc
                        """, "2" },
                { FIRST, getInput( INSTANCE ), "416" },
                { SECOND, """
                        1-3 a: abcde
                        1-3 b: cdefg
                        2-9 c: ccccccccc
                        """, "1"  },
                { SECOND, getInput( INSTANCE ), "688" }
        });
    }
}
