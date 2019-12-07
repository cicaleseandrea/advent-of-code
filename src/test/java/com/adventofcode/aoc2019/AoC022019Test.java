package com.adventofcode.aoc2019;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC022019Test extends Generic {

    private static final Solution INSTANCE = new AoC022019();

    public AoC022019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, "1,0,0,0,99", "2" },
                { FIRST, "2,3,0,3,99", "2" },
                { FIRST, "2,4,4,5,99,0", "2" },
                { FIRST, "1,1,1,4,99,5,6,0,99", "30" },
                { FIRST, getInput( INSTANCE ), "5098658" },
                { SECOND, getInput(INSTANCE), "5064" }
        });
    }
}
