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
public class AoC182016Test extends Generic {

    private static final Solution INSTANCE = new AoC182016();

    public AoC182016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = "{0}({index})->{2}")
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, ".^^.^.^^^^", "38" },
            { FIRST, ".......................................^.......................................", "2863" },
            { FIRST, getInput( INSTANCE ), "1926" },
            { SECOND, getInput( INSTANCE ), "19986699" },
        });
    }
}
