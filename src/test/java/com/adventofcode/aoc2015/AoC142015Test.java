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
public class AoC142015Test extends Generic {

    private static final Solution INSTANCE = new AoC142015();

    public AoC142015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
                        Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
                        """, "1120"},
                {FIRST, getInput(INSTANCE), "2696"},
                {SECOND, """
                        Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
                        Dancer can fly 16 km/s for 11 seconds, but then must rest for 162 seconds.
                        """, "689"},
                {SECOND, getInput(INSTANCE), "1084"},
        });
    }
}
