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
public class AoC152021Test extends Generic {

    private static final Solution INSTANCE = new AoC152021();

    public AoC152021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        1163751742
                        1381373672
                        2136511328
                        3694931569
                        7463417111
                        1319128137
                        1359912421
                        3125421639
                        1293138521
                        2311944581
                        """, "40" },
                { FIRST, getInput( INSTANCE ), "390" },
                { SECOND, """
                        1163751742
                        1381373672
                        2136511328
                        3694931569
                        7463417111
                        1319128137
                        1359912421
                        3125421639
                        1293138521
                        2311944581
                        """, "315" },
                { SECOND, getInput( INSTANCE ), "2814" }
        });
    }
}
