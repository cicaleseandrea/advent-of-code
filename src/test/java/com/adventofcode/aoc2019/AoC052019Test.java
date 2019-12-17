package com.adventofcode.aoc2019;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC052019Test extends Generic {

    private static final Solution INSTANCE = new AoC052019();

    public AoC052019Test( final Type type, final String input, final String result ) {
        super( INSTANCE, type, input, result );
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, getInput(INSTANCE), "5577461" },
                { SECOND, "3,9,8,9,10,9,4,9,99,-1,8", "0" },
                { SECOND, "3,9,7,9,10,9,4,9,99,-1,8", "1" },
                { SECOND, "3,3,1108,-1,8,3,4,3,99", "0" },
                { SECOND, "3,3,1107,-1,8,3,4,3,99", "1" },
                { SECOND, "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", "1" },
                { SECOND, "3,3,1105,-1,9,1101,0,0,12,4,12,99,1", "1" },
                { SECOND,
                  "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99",
                  "999" },
                { SECOND, getInput( INSTANCE ), "7161591" }
        });
    }

}
