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
public class AoC132022Test extends Generic {

    private static final Solution INSTANCE = new AoC132022();

    public AoC132022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        [1,1,3,1,1]
                        [1,1,5,1,1]
                        
                        [[1],[2,3,4]]
                        [[1],4]
                        
                        [9]
                        [[8,7,6]]
                        
                        [[4,4],4,4]
                        [[4,4],4,4,4]
                        
                        [7,7,7,7]
                        [7,7,7]
                        
                        []
                        [3]
                        
                        [[[]]]
                        [[]]
                        
                        [1,[2,[3,[4,[5,6,7]]]],8,9]
                        [1,[2,[3,[4,[5,6,0]]]],8,9]
                        """, "13" },
                { FIRST, getInput( INSTANCE ), "5208" },
                { SECOND, """
                        [1,1,3,1,1]
                        [1,1,5,1,1]
                        
                        [[1],[2,3,4]]
                        [[1],4]
                        
                        [9]
                        [[8,7,6]]
                        
                        [[4,4],4,4]
                        [[4,4],4,4,4]
                        
                        [7,7,7,7]
                        [7,7,7]
                        
                        []
                        [3]
                        
                        [[[]]]
                        [[]]
                        
                        [1,[2,[3,[4,[5,6,7]]]],8,9]
                        [1,[2,[3,[4,[5,6,0]]]],8,9]
                        """, "140" },
                { SECOND, getInput( INSTANCE ), "25792" }
        });
    }
}
