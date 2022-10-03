package com.adventofcode.aoc2019;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC032019Test extends Generic {

    private static final Solution INSTANCE = new AoC032019();

    public AoC032019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        R8,U5,L5,D3
                        U7,R6,D4,L4""", "6" },
                { FIRST, """
                        R75,D30,R83,U83,L12,D49,R71,U7,L72
                        U62,R66,U55,R34,D71,R55,D58,R83""",
                  "159" },
                { FIRST, """
                        R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
                        U98,R91,D20,R16,D67,R40,U7,R15,U6,R7""",
                  "135" },
                { FIRST, getInput( INSTANCE ), "1225" },
                { SECOND, """ 
                        R8,U5,L5,D3
                        U7,R6,D4,L4""", "30" },
                { SECOND, """
                        R75,D30,R83,U83,L12,D49,R71,U7,L72
                        U62,R66,U55,R34,D71,R55,D58,R83""",
                  "610" },
                { SECOND, """
                        R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
                        U98,R91,D20,R16,D67,R40,U7,R15,U6,R7""",
                  "410" },
                { SECOND, getInput( INSTANCE ), "107036" }
        });
    }
}