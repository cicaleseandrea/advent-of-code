package com.adventofcode.aoc2016;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC012016Test extends Generic {

    private static final Solution INSTANCE = new AoC012016();

    public AoC012016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "R2, L3", "5"},
                {FIRST, "R2, R2, R2", "2"},
                {FIRST, "R5, L5, R5, R3", "12"},
                {FIRST, getInput(INSTANCE), "332"},
                {SECOND, "R8, R4, R4, R8", "4"},
                {SECOND, getInput(INSTANCE), "166"},
        });
    }
}
