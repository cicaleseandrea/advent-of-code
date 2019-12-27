package com.adventofcode.aoc2019;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC222019Test extends Generic {

    private static final Solution INSTANCE = new AoC222019();

    public AoC222019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "deal with increment 7\n" + "deal into new stack\n" + "deal into new stack", "3" },
                { FIRST, "cut 6\n" + "deal with increment 7\n" + "deal into new stack", "8" },
                { FIRST, "deal with increment 7\n" + "deal with increment 9\n" + "cut -2", "9" },
                { FIRST, "deal into new stack\n" + "cut -2\n" + "deal with increment 7\n" + "cut 8\n" + "cut -4\n" + "deal with increment 7\n" + "cut 3\n" + "deal with increment 9\n" + "deal with increment 3\n" + "cut -1", "0" },
                { FIRST, getInput( INSTANCE ), "3074" },
				{ SECOND, getInput( INSTANCE ), "104073967000066" }
        });
    }
}
