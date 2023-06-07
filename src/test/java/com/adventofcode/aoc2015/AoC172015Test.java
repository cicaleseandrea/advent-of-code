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
public class AoC172015Test extends Generic {

    private static final Solution INSTANCE = new AoC172015();

    public AoC172015Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                {FIRST, """
                        20
                        15
                        10
                        5
                        5
                        """, "4"},
                {FIRST, getInput(INSTANCE), "1638"},
                {SECOND, """
                        20
                        15
                        10
                        5
                        5
                        """, "3"},
                {SECOND, getInput(INSTANCE), "17"},
        });
    }
}
