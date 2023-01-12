package com.adventofcode.aoc2017;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;
import static com.adventofcode.utils.Utils.MERRY_CHRISTMAS;

import com.adventofcode.Generic;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class AoC252017Test extends Generic {

    private static final Solution INSTANCE = new AoC252017();

    public AoC252017Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
            { FIRST, """
                        Begin in state A.
                        Perform a diagnostic checksum after 6 steps.
                        
                        In state A:
                          If the current value is 0:
                            - Write the value 1.
                            - Move one slot to the right.
                            - Continue with state B.
                          If the current value is 1:
                            - Write the value 0.
                            - Move one slot to the left.
                            - Continue with state B.
                        
                        In state B:
                          If the current value is 0:
                            - Write the value 1.
                            - Move one slot to the left.
                            - Continue with state A.
                          If the current value is 1:
                            - Write the value 1.
                            - Move one slot to the right.
                            - Continue with state A.
                        """, "3" },
            { FIRST, getInput( INSTANCE ), "2794" },
            { SECOND, getInput( INSTANCE ), MERRY_CHRISTMAS }
        });
    }
}
