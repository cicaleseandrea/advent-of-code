package com.adventofcode.aoc2017;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC162017Test extends Generic {

    private static final Solution INSTANCE = new AoC162017();

    public AoC162017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "s1,x3/4,pe/b", "baedc" },
                { FIRST, getInput( INSTANCE ), "padheomkgjfnblic" },
                { SECOND, "s1,x3/4,pe/b", "baedc" },
                { SECOND, getInput( INSTANCE ), "bfcdeakhijmlgopn" }
        });
    }
}
