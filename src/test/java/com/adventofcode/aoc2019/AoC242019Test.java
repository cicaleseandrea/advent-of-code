package com.adventofcode.aoc2019;

import static com.adventofcode.AbstractSolutionTest.Type.FIRST;
import static com.adventofcode.AbstractSolutionTest.Type.SECOND;

import com.adventofcode.AbstractSolutionTest;
import com.adventofcode.Solution;
import java.util.List;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class AoC242019Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC242019();

    public AoC242019Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameterized.Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST,
                        """
                        ....#
                        #..#.
                        #..##
                        ..#..
                        #....""", "2129920" },
                { FIRST, getInput( INSTANCE ), "32776479" },
                { SECOND, getInput( INSTANCE ), "2017" }
        });
    }

}
