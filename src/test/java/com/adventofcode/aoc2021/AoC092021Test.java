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
public class AoC092021Test extends Generic {

    private static final Solution INSTANCE = new AoC092021();

    public AoC092021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        2199943210
                        3987894921
                        9856789892
                        8767896789
                        9899965678
                        """, "15" },
                { FIRST, getInput( INSTANCE ), "496" },
                { SECOND, """
                        2199943210
                        3987894921
                        9856789892
                        8767896789
                        9899965678
                        """, "1134" },
                { SECOND, getInput( INSTANCE ), "902880" }
        });
    }
}
