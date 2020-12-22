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
public class AoC222020Test extends Generic {

    private static final Solution INSTANCE = new AoC222020();

    public AoC222020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        Player 1:
                        9
                        2
                        6
                        3
                        1
                        
                        Player 2:
                        5
                        8
                        4
                        7
                        10""", "306"  },
                { FIRST, getInput( INSTANCE ), "35562" },
                { SECOND, """
                        Player 1:
                        9
                        2
                        6
                        3
                        1
                        
                        Player 2:
                        5
                        8
                        4
                        7
                        10""", "291"  },
                { SECOND, getInput( INSTANCE ), "34424" }
        });
    }
}
