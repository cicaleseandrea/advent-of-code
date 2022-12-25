package com.adventofcode.aoc2022;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC242022Test extends Generic {

    private static final Solution INSTANCE = new AoC242022();

    public AoC242022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        #.######
                        #>>.<^<#
                        #.<..<<#
                        #>v.><>#
                        #<^v^^>#
                        ######.#
                        """, "18" },
                { FIRST, getInput( INSTANCE ), "228" },
                { SECOND, """
                        #.######
                        #>>.<^<#
                        #.<..<<#
                        #>v.><>#
                        #<^v^^>#
                        ######.#
                        """, "54" },
                { SECOND, getInput( INSTANCE ), "723" }
        });
    }
}
