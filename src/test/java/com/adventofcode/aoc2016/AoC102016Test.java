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
public class AoC102016Test extends Generic {

    private static final Solution INSTANCE = new AoC102016();

    public AoC102016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        value 5 goes to bot 2
                        bot 2 gives low to bot 1 and high to bot 0
                        value 3 goes to bot 1
                        bot 1 gives low to output 1 and high to bot 0
                        bot 0 gives low to output 2 and high to output 0
                        value 2 goes to bot 2""", "0" },
                { FIRST, getInput( INSTANCE ), "181" },
                { SECOND, getInput( INSTANCE ), "12567" },
        });
    }
}
