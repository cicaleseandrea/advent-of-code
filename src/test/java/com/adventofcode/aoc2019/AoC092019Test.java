package com.adventofcode.aoc2019;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC092019Test extends Generic {

    private static final Solution INSTANCE = new AoC092019();

    public AoC092019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST,
                  "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99",
                  "109\n1\n204\n-1\n1001\n100\n1\n100\n1008\n100\n16\n101\n1006\n101\n0\n99" },
                { FIRST, "1102,34915192,34915192,7,4,7,99,0", "1219070632396864" },
                { FIRST, "104,1125899906842624,99", "1125899906842624" },
                { FIRST, getInput( INSTANCE ), "4006117640" },
                { SECOND, getInput( INSTANCE ), "88231" }
        });
    }
}
