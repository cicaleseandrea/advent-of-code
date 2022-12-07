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
public class AoC202016Test extends Generic {

    private static final Solution INSTANCE = new AoC202016();

    public AoC202016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        5-8
                        0-2
                        4-7
                        """, "3" },
            { FIRST, getInput( INSTANCE ), "14975795" },
            { SECOND, """
                        5-8
                        0-2
                        4-7
                        """, "4294967288" },
            { SECOND, getInput( INSTANCE ), "101" },
        });
    }
}
