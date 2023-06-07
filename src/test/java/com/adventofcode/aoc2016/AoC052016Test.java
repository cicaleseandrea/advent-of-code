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
public class AoC052016Test extends Generic {

    private static final Solution INSTANCE = new AoC052016();

    public AoC052016Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "abc", "18f47a30" },
                { FIRST, getInput( INSTANCE ), "801b56a7" },
                { SECOND, "abc", "05ace8e3" },
                { SECOND, getInput( INSTANCE ), "424a0197" },
        });
    }
}
