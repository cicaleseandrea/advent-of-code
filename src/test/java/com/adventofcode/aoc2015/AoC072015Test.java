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
public class AoC072015Test extends Generic {

    private static final Solution INSTANCE = new AoC072015();

    public AoC072015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST,
                 """
                 123 -> x
                 456 -> y
                 x AND y -> d
                 x OR y -> e
                 x LSHIFT 2 -> b
                 y RSHIFT 2 -> a
                 NOT x -> h
                 NOT y -> i
                 """, "114"},
                {FIRST, getInput(INSTANCE), "3176"},
                {SECOND, getInput(INSTANCE), "14710"},
        });
    }
}