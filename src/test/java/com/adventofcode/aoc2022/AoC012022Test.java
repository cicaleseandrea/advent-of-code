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
public class AoC012022Test extends Generic {

    private static final Solution INSTANCE = new AoC012022();

    public AoC012022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        1000
                        2000
                        3000
                        
                        4000
                        
                        5000
                        6000
                        
                        7000
                        8000
                        9000
                        
                        10000
                        """, "24000" },
                { FIRST, getInput( INSTANCE ), "68292" },
                { SECOND, """
                        1000
                        2000
                        3000
                        
                        4000
                        
                        5000
                        6000
                        
                        7000
                        8000
                        9000
                        
                        10000
                        """, "45000" },
                { SECOND, getInput( INSTANCE ), "203203" }
        });
    }
}
