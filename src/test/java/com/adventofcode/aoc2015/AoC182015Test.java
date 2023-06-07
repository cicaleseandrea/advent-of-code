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
public class AoC182015Test extends Generic {

    private static final Solution INSTANCE = new AoC182015();

    public AoC182015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        .#.#.#
                        ...##.
                        #....#
                        ..#...
                        #.#..#
                        ####..
                        """, "4"},
                {FIRST, getInput(INSTANCE), "814"},
                {SECOND, """
                        .#.#.#
                        ...##.
                        #....#
                        ..#...
                        #.#..#
                        ####..
                        """, "7"},
                {SECOND, getInput(INSTANCE), "924"},
        });
    }
}
