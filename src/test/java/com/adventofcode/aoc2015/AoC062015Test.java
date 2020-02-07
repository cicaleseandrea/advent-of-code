package com.adventofcode.aoc2015;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC062015Test extends Generic {

    private static final Solution INSTANCE = new AoC062015();

    public AoC062015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "turn on 0,0 through 999,999", "1000000"},
                {FIRST,
                 """
                 turn on 0,0 through 999,999
                 toggle 0,0 through 999,0
                 """, "999000"},
                { FIRST,
                  """
                  turn on 0,0 through 999,999
                  toggle 0,0 through 999,0
                  turn off 499,499 through 500,500
                  """, "998996" },
                {FIRST, getInput(INSTANCE), "377891"},
                {SECOND, "turn on 0,0 through 0,0", "1"},
                {SECOND, "toggle 0,0 through 999,999", "2000000"},
                {SECOND, getInput(INSTANCE), "14110788"},
        });
    }
}