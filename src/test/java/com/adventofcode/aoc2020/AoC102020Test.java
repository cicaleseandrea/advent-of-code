package com.adventofcode.aoc2020;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC102020Test extends Generic {

    private static final Solution INSTANCE = new AoC102020();

    public AoC102020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        16
                        10
                        15
                        5
                        1
                        11
                        7
                        19
                        6
                        12
                        4
                        """, "35" },
                { FIRST, """
                        28
                        33
                        18
                        42
                        31
                        14
                        46
                        20
                        48
                        47
                        24
                        23
                        49
                        45
                        19
                        38
                        39
                        11
                        1
                        32
                        25
                        35
                        8
                        17
                        7
                        9
                        4
                        2
                        34
                        10
                        3
                        """, "220" },
                { FIRST, getInput( INSTANCE ), "1876" },
                { SECOND, """
                        16
                        10
                        15
                        5
                        1
                        11
                        7
                        19
                        6
                        12
                        4
                        """, "8" },
                { SECOND, """
                        28
                        33
                        18
                        42
                        31
                        14
                        46
                        20
                        48
                        47
                        24
                        23
                        49
                        45
                        19
                        38
                        39
                        11
                        1
                        32
                        25
                        35
                        8
                        17
                        7
                        9
                        4
                        2
                        34
                        10
                        3
                        """, "19208"  },
                { SECOND, getInput( INSTANCE ), "0" }
        });
    }
}
