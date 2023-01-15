package com.adventofcode.aoc2016;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC212016Test extends Generic {

    private static final Solution INSTANCE = new AoC212016();

    public AoC212016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        swap position 4 with position 0
                        swap letter d with letter b
                        reverse positions 0 through 4
                        rotate left 1 step
                        move position 1 to position 4
                        move position 3 to position 0
                        rotate based on position of letter b
                        rotate based on position of letter d
                        """, "decab" },
            { FIRST, getInput( INSTANCE ), "bgfacdeh" },
            { SECOND, getInput( INSTANCE ), "bdgheacf" },
        });
    }
}
