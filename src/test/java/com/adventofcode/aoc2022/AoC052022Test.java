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
public class AoC052022Test extends Generic {

    private static final Solution INSTANCE = new AoC052022();

    public AoC052022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                            [D]
                        [N] [C]
                        [Z] [M] [P]
                         1   2   3
                        
                        move 1 from 2 to 1
                        move 3 from 1 to 3
                        move 2 from 2 to 1
                        move 1 from 1 to 2
                        """, "CMZ" },
                { FIRST, getInput( INSTANCE ), "QGTHFZBHV" },
                { SECOND, """
                            [D]
                        [N] [C]
                        [Z] [M] [P]
                         1   2   3
                        
                        move 1 from 2 to 1
                        move 3 from 1 to 3
                        move 2 from 2 to 1
                        move 1 from 1 to 2
                        """, "MCD" },
                { SECOND, getInput( INSTANCE ), "MGDMPSZTM" }
        });
    }
}
