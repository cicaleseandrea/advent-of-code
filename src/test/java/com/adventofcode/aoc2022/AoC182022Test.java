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
public class AoC182022Test extends AbstractSolutionTest {

    private static final Solution INSTANCE = new AoC182022();

    public AoC182022Test(final Type type, final String input, final String result) {
        super(INSTANCE, type, input, result);
    }

    @Parameters(name = PARAMETERS_MESSAGE)
    public static Iterable<Object[]> data() {
        return List.of(new Object[][]{
                { FIRST, """
                        2,2,2
                        1,2,2
                        3,2,2
                        2,1,2
                        2,3,2
                        2,2,1
                        2,2,3
                        2,2,4
                        2,2,6
                        1,2,5
                        3,2,5
                        2,1,5
                        2,3,5
                        """, "64" },
                { FIRST, getInput( INSTANCE ), "3396" },
                { SECOND, """
                        2,2,2
                        1,2,2
                        3,2,2
                        2,1,2
                        2,3,2
                        2,2,1
                        2,2,3
                        2,2,4
                        2,2,6
                        1,2,5
                        3,2,5
                        2,1,5
                        2,3,5
                        """, "58" },
                { SECOND, getInput( INSTANCE ), "2044" }
        });
    }
}
