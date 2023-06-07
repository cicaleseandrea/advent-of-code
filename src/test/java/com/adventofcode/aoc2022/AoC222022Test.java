package com.adventofcode.aoc2022;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class AoC222022Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC222022();

    public AoC222022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                                ...#
                                .#..
                                #...
                                ....
                        ...#.......#
                        ........#...
                        ..#....#....
                        ..........#.
                                ...#....
                                .....#..
                                .#......
                                ......#.
                        
                        10R5L5R10L4R5L5
                        """, "6032" },
                { FIRST, getInput( INSTANCE ), "95358" },
                { SECOND, getInput( INSTANCE ), "144361" }
        });
    }
}
