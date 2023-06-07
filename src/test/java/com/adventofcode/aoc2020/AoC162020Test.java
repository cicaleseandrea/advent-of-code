package com.adventofcode.aoc2020;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC162020Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC162020();

    public AoC162020Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        class: 1-3 or 5-7
                        row: 6-11 or 33-44
                        seat: 13-40 or 45-50
                        
                        your ticket:
                        7,1,14
                        
                        nearby tickets:
                        7,3,47
                        40,4,50
                        55,2,20
                        38,6,12
                        """, "71"  },
                { FIRST, getInput( INSTANCE ), "21071" },
                { SECOND, """
                        class: 1-3 or 5-7
                        row: 6-11 or 33-44
                        seat: 13-40 or 45-50
                        
                        your ticket:
                        7,1,14
                        
                        nearby tickets:
                        7,3,47
                        40,4,50
                        55,2,20
                        38,6,12
                        """, "7"  },
                { SECOND, """
                        class: 0-1 or 4-19
                        row: 0-5 or 8-19
                        seat: 0-13 or 16-19

                        your ticket:
                        11,12,13

                        nearby tickets:
                        3,9,18
                        15,1,5
                        5,14,9
                        """, "132"  },
                { SECOND, getInput( INSTANCE ), "3429967441937" }
        });
    }
}
