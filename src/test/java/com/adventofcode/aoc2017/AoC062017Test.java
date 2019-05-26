package com.adventofcode.aoc2017;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.Solution.getInput;

@RunWith(Parameterized.class)
public class AoC062017Test extends Generic {

    static private Solution INSTANCE = new AoC062017();

    public AoC062017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, "0\t2\t7\t0", "5"},
                {FIRST, getInput(INSTANCE), "12841"},
                {SECOND, "0\t2\t7\t0", "4"},
                {SECOND, getInput(INSTANCE), "8038"}
        });
    }
}