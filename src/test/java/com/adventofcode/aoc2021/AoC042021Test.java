package com.adventofcode.aoc2021;

import static com.adventofcode.Generic.Type.FIRST;
import static com.adventofcode.Generic.Type.SECOND;

import java.util.List;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.adventofcode.Generic;
import com.adventofcode.Solution;

@RunWith(Parameterized.class)
public class AoC042021Test extends Generic {

    private static final Solution INSTANCE = new AoC042021();

    public AoC042021Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
                        
                        22 13 17 11  0
                         8  2 23  4 24
                        21  9 14 16  7
                         6 10  3 18  5
                         1 12 20 15 19
                        
                         3 15  0  2 22
                         9 18 13 17  5
                        19  8  7 25 23
                        20 11 10 24  4
                        14 21 16 12  6
                        
                        14 21 17 24  4
                        10 16 15  9 19
                        18  8 23 26 20
                        22 11 13  6  5
                         2  0 12  3  7
                        """, "4512" },
                { FIRST, getInput( INSTANCE ), "11536" },
                { SECOND, """
                        7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
                        
                        22 13 17 11  0
                         8  2 23  4 24
                        21  9 14 16  7
                         6 10  3 18  5
                         1 12 20 15 19
                        
                         3 15  0  2 22
                         9 18 13 17  5
                        19  8  7 25 23
                        20 11 10 24  4
                        14 21 16 12  6
                        
                        14 21 17 24  4
                        10 16 15  9 19
                        18  8 23 26 20
                        22 11 13  6  5
                         2  0 12  3  7
                        """, "1924" },
                { SECOND, getInput( INSTANCE ), "1284" }
        });
    }
}